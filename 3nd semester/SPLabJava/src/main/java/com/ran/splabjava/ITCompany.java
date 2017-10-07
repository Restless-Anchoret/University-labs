package com.ran.splabjava;

import java.util.Collections;
import java.util.List;

public class ITCompany {

    private String name;
    private List<Programmer> programmers;

    public ITCompany(String name, List<Programmer> programmers) {
        this.name = name;
        this.programmers = programmers;
    }

    public void sort() {
        Collections.sort(programmers);
    }

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

}
