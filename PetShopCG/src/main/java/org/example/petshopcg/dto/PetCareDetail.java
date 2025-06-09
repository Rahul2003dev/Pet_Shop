package org.example.petshopcg.dto;

public class PetCareDetail {
    public String type;      // "vaccination" or "grooming"
    public String name;      // vaccine name or grooming service name
    public String dateOrDuration; // vaccination date or grooming duration
    public Integer price;    // price only for grooming; null for vaccination
    public String nextDue;   // only for vaccination; null for grooming

    public PetCareDetail(String type, String name, String dateOrDuration, Integer price, String nextDue) {
        this.type = type;
        this.name = name;
        this.dateOrDuration = dateOrDuration;
        this.price = price;
        this.nextDue = nextDue;
    }
}
