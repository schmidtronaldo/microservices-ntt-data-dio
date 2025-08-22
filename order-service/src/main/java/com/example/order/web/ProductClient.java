package com.example.order.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/produtos/{id}")
    ProductDTO getById(@PathVariable("id") Long id);

    record ProductDTO(Long id, String nome, String descricao, BigDecimal preco) {}
}
