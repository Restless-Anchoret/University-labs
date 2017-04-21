package com.ran.dissertation.main;

import com.ran.dissertation.controller.MainController;
import com.ran.dissertation.factories.DefaultWorldFactory;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        new MainController(Collections.singletonList(
                DefaultWorldFactory.getInstance()
        )).startApplication();
    }

}