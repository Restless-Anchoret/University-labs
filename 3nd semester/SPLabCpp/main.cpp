#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

enum Position {
    JUNIOR,
    MIDDLE,
    SENIOR
};


class Programmer {

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

    string getName() {
        return name;
    }

    string getSurname() {
        return surname;
    }

    Position getPosition() {
        return position;
    }

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

    string getSkillsAsString() {
        string result = skills[0] + " ";
        for (int i = 1; i < skills.size(); i++) {
            result += skills[i] + " ";
        }
        return result;
    }

    virtual void print() = 0;

};

bool compareProgrammers(Programmer* first, Programmer* second) {
    if (first->getPosition() != second->getPosition()) {
        return first->getPosition() < second->getPosition();
    } else if (first->getSurname() != second->getSurname()) {
        return first->getSurname() < second->getSurname();
    } else {
        return first->getName() < second->getName();
    }
}


class Developer : public Programmer {

private:
    string knowledgeArea;
    string currentProject;

public:
    Developer(string name, string surname, Position position, vector<string> skills,
              string knowledgeArea, string currentProject) : Programmer(name, surname, position, skills) {
        this->knowledgeArea = knowledgeArea;
        this-> currentProject = currentProject;
    }

    string getKnowledgeArea() {
        return knowledgeArea;
    }

    string getCurrentProject() {
        return currentProject;
    }

    void print() {
        cout << "Developer: " << getSurname() << " " << getName() << endl;
        cout << "Position: " << getPositionAsString() << " " << endl;
        cout << "Skills: " << getSkillsAsString() << endl;
        cout << "Knowledge area: " << getKnowledgeArea() << endl;
        cout << "Current project: " << getCurrentProject() << endl;
    }

};


class Manager : public Programmer {

private:
    vector<string> projects;
    int subordinatesQuantity;

public:
    Manager(string name, string surname, Position position, vector<string> skills,
            vector<string> projects, int subordinatesQuantity) : Programmer(name, surname, position, skills) {
        this->projects = projects;
        this->subordinatesQuantity = subordinatesQuantity;
    }

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

    void print() {
        cout << "Manager: " << getSurname() << " " << getName() << endl;
        cout << "Position: " << getPositionAsString() << " " << endl;
        cout << "Skills: " << getSkillsAsString() << endl;
        cout << "Projects: " << getProjectsAsString() << endl;
        cout << "Subordinates quantity: " << getSubordinatesQuantity() << endl;
    }

};


class ITCompany {

private:
    string name;
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
    delete company;

    return 0;
}
