package com.babacar.app.controller;

import com.babacar.app.dto.ListResponse;
import com.babacar.app.dto.requests.ProductRequest;
import com.babacar.app.dto.responses.ProductResponse;
import com.babacar.app.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "products APIs")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "creation of a product")
    public ProductResponse create(
            @RequestBody ProductRequest request){
        return productService.create(request);

    }

    @GetMapping("/{uuid}")
    @Operation(summary = "get a product by uuid")
    public ProductResponse create(
            @PathVariable(name = "uuid") String uuid){
        return productService.getByUuid(uuid);

    }


    @DeleteMapping("/{uuid}")
    @Operation(summary = "delete a product by uuid")
    public void delete(
         @PathVariable(name = "uuid")  String uuid){
        productService.delete(uuid);
    }

    @GetMapping("/all")
    @Operation(summary = "get all products with pagination")
    public ListResponse<?> getAll(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size
    ){
        return productService.getAll(page,size);
    }
}
