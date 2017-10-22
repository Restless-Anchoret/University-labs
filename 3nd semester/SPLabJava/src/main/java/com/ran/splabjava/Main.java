package com.ran.splabjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        ITCompany itCompany = readInfoFromFile();
        itCompany.sort();
        itCompany.print();

        System.out.println("Without not odd:");
        itCompany.removeNotOdd();
        itCompany.print();
    }

    // Чтение изначального содержимого контейнера из файла
    private static ITCompany readInfoFromFile() throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("input.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String companyName = reader.readLine();
        int quantity = Integer.parseInt(reader.readLine());
        reader.readLine();
        List<Programmer> programmers = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            String type = reader.readLine();
            String name = reader.readLine();
            String surname = reader.readLine();
            Position position = Position.valueOf(reader.readLine());
            List<String> skills = Arrays.asList(reader.readLine().split(" "));
            if (type.equals("Developer")) {
                String knowledgeArea = reader.readLine();
                String currentProject = reader.readLine();
                programmers.add(new Developer(name, surname, position, skills, knowledgeArea, currentProject));
            } else if (type.equals("Manager")) {
                List<String> projects = Arrays.asList(reader.readLine().split(" "));
                int subordinatesQuantity = Integer.parseInt(reader.readLine());
                programmers.add(new Manager(name, surname, position, skills, projects, subordinatesQuantity));
            }
            reader.readLine();
        }
        return new ITCompany(companyName, programmers);
    }

}
