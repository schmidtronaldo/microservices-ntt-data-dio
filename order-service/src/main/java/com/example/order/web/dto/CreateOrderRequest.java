package com.example.order.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(
    @NotEmpty List<Item> itens
) {
    public record Item(@NotNull Long productId, @NotNull Integer quantidade) {}
}
