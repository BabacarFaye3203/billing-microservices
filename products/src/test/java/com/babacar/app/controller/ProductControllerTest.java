package com.babacar.app.controller;

import com.babacar.app.dto.ListResponse;
import com.babacar.app.dto.requests.ProductRequest;
import com.babacar.app.dto.responses.ProductResponse;
import com.babacar.app.entities.Products;
import com.babacar.app.exception.ProductNotFoundException;
import com.babacar.app.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;
    List<Products> products=new ArrayList<>();
    List<ProductResponse> list=new ArrayList<>();


    @BeforeEach
    public void setup(){
        Products product1=Products.builder().id(1L).uuid("prod1").name("hp").description("16Gb 256Gb").price(10000).quantity(12).build();
        Products product2=Products.builder().id(1L).uuid("prod2").name("savon noir").description("a base d'argan").price(100).quantity(120).build();
        products= List.of(product1,product2);

        ProductResponse response1=ProductResponse.builder().uuid("prod1").name("hp").description("16Gb 256Gb").price(10000).quantity(12).build();
        ProductResponse response2=ProductResponse.builder().uuid("prod2").name("savon noir").description("a base d'argan").price(100).quantity(120).build();
        list=List.of(response1,response2);
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        ProductRequest productRequest= ProductRequest.builder().name("hp").description("16Gb 256Gb").price(10000).quantity(12).build();
        ProductResponse productResponse=list.get(0);
                String json= """
                {
                  "uuid": "prod1",
                  "name": "hp",
                  "description": "16Gb 256Gb",
                  "price": 10000,
                  "quantity": 12
                }
                """;
        String jsonRequest= """
                {
                  "name": "hp",
                  "description": "16Gb 256Gb",
                  "price": 10000,
                  "quantity": 12
                }
                """;

        when(productService.create(any(ProductRequest.class))).thenReturn(productResponse);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json))
                .andExpect(jsonPath("$.name").value("hp"))
                .andExpect(jsonPath("$.quantity").value(12));

        verify(productService).create(any(ProductRequest.class));
    }

    @Test
    public void shouldReturnProductWithValidUuid() throws Exception {
        String validUuid="prod1";
        ProductResponse productResponse=list.get(0);
        when(productService.getByUuid(validUuid)).thenReturn(productResponse);

        String json= """
                {
                  "uuid": "prod1",
                  "name": "hp",
                  "description": "16Gb 256Gb",
                  "price": 10000,
                  "quantity": 12
                }
                """;

        mockMvc.perform(get("/api/v1/products/"+validUuid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json))
                .andExpect(jsonPath("$.name").value("hp"))
                .andExpect(jsonPath("$.price").value(10000));

        verify(productService).getByUuid(validUuid);
    }

    @Test
    public void ShouldThrowProductNotFoundException() throws Exception {
        String invalidUuid = "dhdjd";
        String res= """
                {"message":"product not found","code":404,"status":"NOT_FOUND"}
                """;
        when(productService.getByUuid(invalidUuid)).thenThrow(new ProductNotFoundException("product not found"));

        mockMvc.perform(get("/api/v1/products/"+invalidUuid))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(res));
        verify(productService).getByUuid(invalidUuid);

    }
    @Test
    public void ShouldReturnListOfProducts() throws Exception {
        String json= """
                {
                content:[
                  {
                    "uuid": "prod1",
                    "name": "hp",
                    "description": "16Gb 256Gb",
                    "price": 10000,
                    "quantity": 12
                  },
                  {
                    "uuid": "prod2",
                    "name": "savon noir",
                    "description": "a base d'argan",
                    "price": 100,
                    "quantity": 120
                  }
                ],
                pageNumber:0,
                pageSize:2,
                totalElements:2,
                totalPages:1,
                hasNext:false
                }
                """;
        ListResponse<ProductResponse> listResponse=new ListResponse<>(
                list,
                0,
                2,
                2,
                1,
                false
        );
        when(productService.getAll(0,10)).thenReturn(listResponse);

        mockMvc.perform(get("/api/v1/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        verify(productService).getAll(0,10);

    }
}