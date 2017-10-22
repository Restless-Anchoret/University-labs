package com.ran.splabjava;

import java.util.Collections;
import java.util.List;

// Класс-контейнер
public class ITCompany {

    private String name;
    // Список экземпляров базового класса
    private List<Programmer> programmers;

    public ITCompany(String name, List<Programmer> programmers) {
        this.name = name;
        this.programmers = programmers;
    }

    public void sort() {
        Collections.sort(programmers);
    }

    // Вывод в консоль содержимого контейнера
    public void print() {
        System.out.println("IT company: " + name);
        System.out.println();
        programmers.forEach(programmer -> {
            if (programmer instanceof Developer) {
                System.out.println("[Info about Developer]");
            } else if (programmer instanceof Manager) {
                System.out.println("[Info about Manager]");
            }
            programmer.print();
            System.out.println();
        });
    }

    // Удаление разработчиков с четными номерами проектов
    public void removeNotOdd() {
        programmers.removeIf(programmer -> {
            if (programmer instanceof Manager) {
                return false;
            } else {
                String project = ((Developer)programmer).getCurrentProject();
                int number = Integer.parseInt(project.substring(project.length() - 1));
                return number % 2 == 0;
            }
        });
    }

}
