package com.ran.splabjava;

import java.util.List;

public class Manager extends Programmer {

    private List<String> projects;
    private int subordinatesQuantity;

    public Manager(String name, String surname, Position position, List<String> skills,
                   List<String> projects, int subordinatesQuantity) {
        super(name, surname, position, skills);
        this.projects = projects;
        this.subordinatesQuantity = subordinatesQuantity;
    }

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

    @Override
    void print() {
        System.out.println("Manager: " + getSurname() + " " + getName());
        System.out.println("Position: " + getPosition());
        System.out.println("Skills: " + getSkillsAsString());
        System.out.println("Projects: " + getProjectsAsString());
        System.out.println("Subordinates quantity: " + getSubordinatesQuantity());
    }

}
