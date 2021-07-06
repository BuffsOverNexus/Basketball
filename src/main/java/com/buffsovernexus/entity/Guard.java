package com.buffsovernexus.entity;

public class Guard {

    private String firstName;
    private String lastName;
    private int fourPointer;

    public Guard() {}

    public Guard(String firstName, String lastName, int fourPointer) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setFourPointer(fourPointer);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getFourPointer() {
        return fourPointer;
    }

    public void setFourPointer(int fourPointer) {
        this.fourPointer = fourPointer;
    }
}
