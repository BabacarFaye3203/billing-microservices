package com.babacar.app.dto.requests;

public record ProductRequest (
        String name,
        String description,
        double price,
        double quantity
){
}
