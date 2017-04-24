package com.ran.dissertation.main;

import com.ran.dissertation.controller.MainController;
import com.ran.dissertation.factories.DefaultWorldFactory;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        new MainController(
                Arrays.asList(DefaultWorldFactory.getInstance())
        ).startApplication();
    }

}