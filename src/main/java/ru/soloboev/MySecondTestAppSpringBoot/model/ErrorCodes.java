package ru.soloboev.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {
    EMPTY(""),
    VALIDATION("ValidationException"),
    UNSUPPORTED("UnknownException"),
    UNKNOWN("UnsupportedException");
    private final String name;
    ErrorCodes(String name){
        this.name = name;
    }
    @JsonValue
    public String getName(){
        return name;
    }
    @Override
    public String toString(){
        return name;
    }
}
