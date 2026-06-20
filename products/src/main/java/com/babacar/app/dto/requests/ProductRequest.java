package com.babacar.app.dto.requests;

import lombok.Builder;

@Builder
public record ProductRequest (
        String name,
        String description,
        double price,
        double quantity
){
}
