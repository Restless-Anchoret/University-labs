package com.ran.splabjava;

import java.util.List;

// Класс-наследник
public class Developer extends Programmer {

    // Приватные свойства класса наследника
    private String knowledgeArea;
    private String currentProject;

    public Developer(String name, String surname, Position position, List<String> skills,
                     String knowledgeArea, String currentProject) {
        super(name, surname, position, skills);
        this.knowledgeArea = knowledgeArea;
        this.currentProject = currentProject;
    }

    // Get- и Set-методы
    public String getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(String knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    public String getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }

    // Переопределенный метод базового класса
    @Override
    void print() {
        System.out.println("Developer: " + getSurname() + " " + getName());
        System.out.println("Position: " + getPosition());
        System.out.println("Skills: " + getSkillsAsString());
        System.out.println("Knowledge area: " + getKnowledgeArea());
        System.out.println("Current project: " + getCurrentProject());
    }

}
