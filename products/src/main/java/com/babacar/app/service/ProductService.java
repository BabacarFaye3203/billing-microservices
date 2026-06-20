package com.babacar.app.service;

import com.babacar.app.dto.ListResponse;
import com.babacar.app.dto.requests.ProductRequest;
import com.babacar.app.dto.responses.ProductResponse;
import com.babacar.app.entities.Products;
import com.babacar.app.exception.ProductNotFoundException;
import com.babacar.app.mapper.ProductMapper;
import com.babacar.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest request){
        Products product=new Products();
        product.setUuid(UUID.randomUUID().toString());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());

        Products saved=productRepository.save(product);
        return new ProductResponse(
                saved.getUuid(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getQuantity()
        );
    }

    public ProductResponse getByUuid(String uuid){
        Products saved=productRepository.findByUuid(uuid)
                .orElseThrow(()->new ProductNotFoundException("product not fount"));
        return new ProductResponse(
                saved.getUuid(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getQuantity()
        );
    }

    public void delete(String uuid){
        Products product=productRepository.findByUuid(uuid)
                .orElseThrow(()->new ProductNotFoundException("product not fount"));
        productRepository.delete(product);
    }

    public ListResponse<ProductResponse> getAll(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Products> products=productRepository.findAllProducts(pageable);

        List<ProductResponse> content=productRepository.findAllProducts(pageable)
                .stream()
                .map(saved->new ProductResponse(
                        saved.getUuid(),
                        saved.getName(),
                        saved.getDescription(),
                        saved.getPrice(),
                        saved.getQuantity())
                )
                .toList();

        return new ListResponse<ProductResponse>(
                content,
                products.getNumber(),
                content.size(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.hasNext()
        );

    }



}
