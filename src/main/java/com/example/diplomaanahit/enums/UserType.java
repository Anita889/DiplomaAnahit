package com.example.diplomaanahit.enums;


public enum UserType {
    STUDENT("STUDENT"),
    LECTURER("LECTURER"),
    ADMIN("ADMIN"),

    USER("USER");
    private String label;

    UserType(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static UserType getByLabel(String label) {
        for (UserType userType : values()) {
            if (userType.getLabel().equals(label)) {
                return userType;
            }
        }
        return null;
    }


    }
