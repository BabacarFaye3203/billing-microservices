package com.babacar.app.dto.responses;

public record ProductResponse (
        String uuid,
        String name,
        String description,
        double price,
        double quantity
){
}
