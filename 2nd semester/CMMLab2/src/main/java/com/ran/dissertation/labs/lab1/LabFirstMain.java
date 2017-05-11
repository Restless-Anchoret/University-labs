package com.ran.dissertation.labs.lab1;

import java.awt.EventQueue;

public class LabFirstMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LabFirstMainController mainController = new LabFirstMainController();
            mainController.startApplication();
        });
    }
    
}