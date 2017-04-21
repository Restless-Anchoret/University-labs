package com.ran.dissertation.ui;

public class SelectItem<T> {

    private T value;
    private String presentation;

    public SelectItem(T value, String presentation) {
        this.value = value;
        this.presentation = presentation;
    }

    public T getValue() {
        return value;
    }

    public String getPresentation() {
        return presentation;
    }
    
}