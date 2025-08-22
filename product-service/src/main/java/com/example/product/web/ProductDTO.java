package com.example.product.web;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductDTO(
    Long id,
    @NotBlank String nome,
    @NotBlank String descricao,
    @NotNull @DecimalMin("0.0") BigDecimal preco
) {}
