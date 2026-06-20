package com.babacar.app.service;

import com.babacar.app.dto.ListResponse;
import com.babacar.app.dto.requests.ProductRequest;
import com.babacar.app.dto.responses.ProductResponse;
import com.babacar.app.entities.Products;
import com.babacar.app.exception.ProductNotFoundException;
import com.babacar.app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    List<Products> products=new ArrayList<>();
    List<ProductResponse> list=new ArrayList<>();

    @BeforeEach
    public void setup(){
        Products product1=Products.builder().id(1L).uuid("prod1").name("hp").description("16Gb 256Gb").price(10000).quantity(12).build();
        Products product2=Products.builder().id(1L).uuid("prod2").name("savon noir").description("a base d'argan").price(100).quantity(120).build();
       products=List.of(product1,product2);

       ProductResponse response1=ProductResponse.builder().uuid("prod1").name("hp").description("16Gb 256Gb").price(10000).quantity(12).build();
       ProductResponse response2=ProductResponse.builder().uuid("prod2").name("savon noir").description("a base d'argan").price(100).quantity(120).build();
       list=List.of(response1,response2);
    }

    @Test
    public void shouldCreateProduct(){
        Products prod=products.get(0);

        ProductRequest productRequest= ProductRequest.builder().name("hp").description("16Gb 256Gb").price(10000).quantity(12).build();
        when(productRepository.save(any(Products.class))).thenReturn(prod);

        ProductResponse expected=productService.create(productRequest);

        assertEquals(expected.description(),prod.getDescription());
        assertEquals(expected.uuid(),prod.getUuid());
        verify(productRepository).save(any(Products.class));

    }

    @Test
    public void shouldReturnProductWithValidUuid(){
        String validUuid="prod1";
        Products prod=products.get(0);
        when(productRepository.findByUuid(validUuid)).thenReturn(Optional.of(prod));

        ProductResponse expected=productService.getByUuid(validUuid);

        verify(productRepository).findByUuid(validUuid);
        assertEquals(expected.uuid(),prod.getUuid());
        assertEquals(expected.name(),prod.getName());
    }

    @Test
    public void ShouldThrowProductNotFoundException(){
        String invalidUuid="dhdjd";
        when(productRepository.findByUuid(invalidUuid)).thenThrow(new ProductNotFoundException("product not found"));

        assertThrows(ProductNotFoundException.class,()->productService.getByUuid(invalidUuid));

        verify(productRepository).findByUuid(invalidUuid);
    }

    @Test
    public void shouldReturnListOfProducts(){
        Pageable pageable= PageRequest.of(0,10);
        Page<Products> productsPage=new PageImpl<>(products,pageable,2);

        when(productRepository.findAllProducts(pageable)).thenReturn(productsPage);

        ListResponse<ProductResponse> listProducts=productService.getAll(0,10);

        assertNotNull(listProducts);
        assertEquals(listProducts.content(),list);
        assertEquals(2,listProducts.content().size());

    }

}