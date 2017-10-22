#include "stdafx.h"
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <stdlib.h>

using namespace std;

enum Position {
    JUNIOR,
    MIDDLE,
    SENIOR
};

// Базовый класс
class Programmer {

// Приватные свойства базового класса
private:
    string name;
    string surname;
    Position position;
    vector<string> skills;

public:
    Programmer(string name, string surname, Position position, vector<string> skills) {
        this->name = name;
        this->surname = surname;
        this->position = position;
        this->skills = skills;
    }

	// Get- и Set-методы
    string getName() {
        return name;
    }

    string getSurname() {
        return surname;
    }

    Position getPosition() {
        return position;
    }

	// Метод в базовом классе, вызываемый классами-наследниками
    string getPositionAsString() {
        if (position == JUNIOR) {
            return "JUNIOR";
        } else if (position == MIDDLE) {
            return "MIDDLE";
        } else {
            return "SENIOR";
        }
    }

    vector<string> getSkills() {
        return skills;
    }

	// Метод в базовом классе, вызываемый классами-наследниками
    string getSkillsAsString() {
        string result = skills[0] + " ";
        for (int i = 1; i < skills.size(); i++) {
            result += skills[i] + " ";
        }
        return result;
    }

	// Абстрактный метод базового класса
    virtual void print() = 0;

};

// Метод, определяющий правило для сортировки
bool compareProgrammers(Programmer* first, Programmer* second) {
    if (first->getPosition() != second->getPosition()) {
        return first->getPosition() < second->getPosition();
    } else if (first->getSurname() != second->getSurname()) {
        return first->getSurname() < second->getSurname();
    } else {
        return first->getName() < second->getName();
    }
}


// Класс-наследник
class Developer : public Programmer {

// Приватные свойства класса наследника
private:
    string knowledgeArea;
    string currentProject;

public:
    Developer(string name, string surname, Position position, vector<string> skills,
              string knowledgeArea, string currentProject) : Programmer(name, surname, position, skills) {
        this->knowledgeArea = knowledgeArea;
        this-> currentProject = currentProject;
    }

	// Get- и Set-методы
    string getKnowledgeArea() {
        return knowledgeArea;
    }

    string getCurrentProject() {
        return currentProject;
    }

	// Переопределенный метод базового класса
    void print() {
        cout << "Developer: " << getSurname() << " " << getName() << endl;
        cout << "Position: " << getPositionAsString() << " " << endl;
        cout << "Skills: " << getSkillsAsString() << endl;
        cout << "Knowledge area: " << getKnowledgeArea() << endl;
        cout << "Current project: " << getCurrentProject() << endl;
    }

};


// Класс-наследник
class Manager : public Programmer {

// Приватные свойства класса наследника
private:
    vector<string> projects;
    int subordinatesQuantity;

public:
    Manager(string name, string surname, Position position, vector<string> skills,
            vector<string> projects, int subordinatesQuantity) : Programmer(name, surname, position, skills) {
        this->projects = projects;
        this->subordinatesQuantity = subordinatesQuantity;
    }

	// Get- и Set-методы
    vector<string> getProjects() {
        return projects;
    }

    int getSubordinatesQuantity() {
        return subordinatesQuantity;
    }

    string getProjectsAsString() {
        string result = projects[0] + " ";
        for (int i = 1; i < projects.size(); i++) {
            result += projects[i] + " ";
        }
        return result;
    }

	// Переопределенный метод базового класса
    void print() {
        cout << "Manager: " << getSurname() << " " << getName() << endl;
        cout << "Position: " << getPositionAsString() << " " << endl;
        cout << "Skills: " << getSkillsAsString() << endl;
        cout << "Projects: " << getProjectsAsString() << endl;
        cout << "Subordinates quantity: " << getSubordinatesQuantity() << endl;
    }

};


// Класс-контейнер
class ITCompany {

private:
    string name;
	// Список экземпляров базового класса
    vector<Programmer*> programmers;

public:
    ITCompany(string name, vector<Programmer*> programmers) {
        this->name = name;
        this->programmers = programmers;
    }

    ~ITCompany() {
        for (int i = 0; i < programmers.size(); i++) {
            delete programmers[i];
        }
    }

    void sort() {
        std::sort(programmers.begin(), programmers.end(), compareProgrammers);
    }

	// Вывод в консоль содержимого контейнера
    void print() {
        cout << "IT Company: " << name << endl << endl;
        for (int i = 0; i < programmers.size(); i++) {
            if (Developer* developer = dynamic_cast<Developer*>(programmers[i])) {
                cout << "[Info about Developer]" << endl;
            } else if (Manager* manager = dynamic_cast<Manager*>(programmers[i])) {
                cout << "[Info about Manager]" << endl;
            }
            programmers[i]->print();
            cout << endl;
        }
    }

	// Удаление разработчиков с четными номерами проектов
	void removeNotOdd() {
		vector<Programmer*> result;
		for (int i = 0; i < programmers.size(); i++) {
			if (Developer* developer = dynamic_cast<Developer*>(programmers[i])) {
				string project = developer->getCurrentProject();
				char * arr = new char[1];
				arr[0] = project[project.length() - 1];
				int number = atoi(arr);
				if (number % 2 == 1) {
					result.push_back(programmers[i]);
				}
            } else if (Manager* manager = dynamic_cast<Manager*>(programmers[i])) {
				result.push_back(programmers[i]);
            }
		}
		programmers = result;
	}

};


string getString(ifstream& in) {
    string s;
    getline(in, s);
    return s;
}

int getInt(ifstream& in) {
    int number;
    in >> number;
    getString(in);
    return number;
}

Position getPosition(ifstream& in) {
    string s = getString(in);
    if (s == "JUNIOR") {
        return JUNIOR;
    } else if (s == "MIDDLE") {
        return MIDDLE;
    } else {
        return SENIOR;
    }
}

vector<string> getWords(ifstream& in) {
    string s = getString(in);
    vector<string> result;
    int start = 0;
    int end = s.find(" ");
    while (end != std::string::npos) {
        result.push_back(s.substr(start, end - start));
        start = end + 1;
        end = s.find(" ", start);
    }
    result.push_back(s.substr(start, end));
    return result;
}

// Чтение изначального содержимого контейнера из файла
ITCompany* readInfoFromFile() {
    ifstream in("input.txt");
    string name = getString(in);
    int programmersQuantity = getInt(in);
    getString(in);
    vector<Programmer*> programmers;
    for (int i = 0; i < programmersQuantity; i++) {
        string type = getString(in);
        string name = getString(in);
        string surname = getString(in);
        Position position = getPosition(in);
        vector<string> skills = getWords(in);
        if (type == "Developer") {
            string knowledgeArea = getString(in);
            string currentProject = getString(in);
            programmers.push_back(new Developer(name, surname, position, skills, knowledgeArea, currentProject));
        } else if (type == "Manager") {
            vector<string> projects = getWords(in);
            int subordinatesQuantity = getInt(in);
            programmers.push_back(new Manager(name, surname, position, skills, projects, subordinatesQuantity));
        }
        getString(in);
    }
    return new ITCompany(name, programmers);
}


int main()
{
    ITCompany* company = readInfoFromFile();
    company->sort();
    company->print();

	cout << "Without not odd:" << endl;
	company->removeNotOdd();
	company->print();

    delete company;

	cin.get();
    return 0;
	
}
