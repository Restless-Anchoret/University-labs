package com.ran.splabjava;

import java.util.List;

// Класс-наследник
public class Manager extends Programmer {

    // Приватные свойства класса наследника
    private List<String> projects;
    private int subordinatesQuantity;

    public Manager(String name, String surname, Position position, List<String> skills,
                   List<String> projects, int subordinatesQuantity) {
        super(name, surname, position, skills);
        this.projects = projects;
        this.subordinatesQuantity = subordinatesQuantity;
    }

    // Get- и Set-методы
    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public int getSubordinatesQuantity() {
        return subordinatesQuantity;
    }

    public void setSubordinatesQuantity(int subordinatesQuantity) {
        this.subordinatesQuantity = subordinatesQuantity;
    }

    public String getProjectsAsString() {
        return projects.stream()
                .reduce((first, second) -> first + ", " + second)
                .get();
    }

    // Переопределенный метод базового класса
    @Override
    void print() {
        System.out.println("Manager: " + getSurname() + " " + getName());
        System.out.println("Position: " + getPosition());
        System.out.println("Skills: " + getSkillsAsString());
        System.out.println("Projects: " + getProjectsAsString());
        System.out.println("Subordinates quantity: " + getSubordinatesQuantity());
    }

}
