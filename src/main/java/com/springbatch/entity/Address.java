package com.springbatch.entity;


import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;
    private String pin;

    // Constructors
    public Address() {
        // Default constructor required by JPA
    }

    public Address(String city, String street, String pin) {
        this.city = city;
        this.street = street;
        this.pin = pin;
    }

    // Getters and Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    // toString method
    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
