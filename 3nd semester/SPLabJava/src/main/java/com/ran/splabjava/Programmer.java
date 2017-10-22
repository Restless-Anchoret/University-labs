package com.ran.splabjava;

import java.util.List;

// Базовый класс
abstract public class Programmer implements Comparable<Programmer> {

    // Приватные свойства базового класса
    private String name;
    private String surname;
    private Position position;
    private List<String> skills;

    public Programmer(String name, String surname, Position position, List<String> skills) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.skills = skills;
    }

    // Get- и Set-методы
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    // Абстрактный метод базового класса
    abstract void print();

    // Метод в базовом классе, вызываемый классами-наследниками
    public String getSkillsAsString() {
        return skills.stream()
                .reduce((first, second) -> first + ", " + second)
                .get();
    }

    // Метод, определяющий правило для сортировки
    public int compareTo(Programmer other) {
        if (this.position != other.position) {
            return Integer.compare(this.position.ordinal(), other.position.ordinal());
        } else if (!this.surname.equals(other.surname)) {
            return this.surname.compareTo(other.surname);
        } else {
            return this.name.compareTo(other.name);
        }
    }

}
