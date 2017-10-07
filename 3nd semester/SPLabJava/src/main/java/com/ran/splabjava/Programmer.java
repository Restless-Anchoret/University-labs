package com.ran.splabjava;

import java.util.List;

abstract public class Programmer implements Comparable<Programmer> {

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

    abstract void print();

    public String getSkillsAsString() {
        return skills.stream()
                .reduce((first, second) -> first + ", " + second)
                .get();
    }

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
