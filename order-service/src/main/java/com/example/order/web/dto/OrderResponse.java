package com.example.order.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(Long id, Instant createdAt, BigDecimal total, List<Item> itens) {
    public record Item(Long productId, String nomeProduto, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {}
}
