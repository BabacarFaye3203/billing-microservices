package com.babacar.app.mapper;

import com.babacar.app.dto.responses.ProductResponse;
import com.babacar.app.entities.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Products product);
}
