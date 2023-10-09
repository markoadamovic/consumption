package com.example.consumption.model.enums;

public enum EntityStatus {

    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private final String name;

    EntityStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
