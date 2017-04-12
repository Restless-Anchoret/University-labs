package com.ran.dissertation.labs.cmm;

import java.util.Arrays;

public class NewtonMethodConcreteTask {

    public ConcreteTaskDecision solve() {
        return new ConcreteTaskDecision(x -> x, Arrays.asList());
    }

}