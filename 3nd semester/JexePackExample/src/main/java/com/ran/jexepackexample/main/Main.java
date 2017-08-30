package com.ran.jexepackexample.main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static java.nio.file.StandardOpenOption.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Path outputFilePath = Paths.get("./output/output.txt");
        Path parentDirectory = outputFilePath.getParent();
        if (!Files.exists(parentDirectory)) {
            Files.createDirectories(parentDirectory);
        }

        OutputStream stream = Files.newOutputStream(outputFilePath, CREATE, APPEND);
        PrintWriter writer = new PrintWriter(stream);
        writer.println("Current date is: " + new Date());
        writer.close();
    }

}
