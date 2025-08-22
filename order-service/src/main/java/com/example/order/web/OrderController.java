package com.example.order.web;

import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import com.example.order.repo.OrderRepository;
import com.example.order.web.dto.CreateOrderRequest;
import com.example.order.web.dto.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final OrderRepository repo;
    private final ProductClient productClient;

    public OrderController(OrderRepository repo, ProductClient productClient) {
        this.repo = repo;
        this.productClient = productClient;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest req) {
        Order order = new Order();
        BigDecimal total = BigDecimal.ZERO;

        for (CreateOrderRequest.Item it : req.itens()) {
            ProductClient.ProductDTO product = productClient.getById(it.productId());
            if (product == null) {
                return ResponseEntity.badRequest().build();
            }
            OrderItem oi = new OrderItem();
            oi.setProductId(product.id());
            oi.setNomeProduto(product.nome());
            oi.setQuantidade(it.quantidade());
            oi.setPrecoUnitario(product.preco());
            BigDecimal subtotal = product.preco().multiply(BigDecimal.valueOf(it.quantidade()));
            oi.setSubtotal(subtotal);
            order.getItems().add(oi);
            total = total.add(subtotal);
        }

        order.setTotal(total);
        Order saved = repo.save(order);
        return ResponseEntity.created(URI.create("/pedidos/" + saved.getId())).body(toResponse(saved));
    }

    @GetMapping
    public List<OrderResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
        return repo.findById(id).map(o -> ResponseEntity.ok(toResponse(o)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private OrderResponse toResponse(Order o) {
        List<OrderResponse.Item> items = o.getItems().stream()
                .map(oi -> new OrderResponse.Item(
                        oi.getProductId(), oi.getNomeProduto(), oi.getQuantidade(),
                        oi.getPrecoUnitario(), oi.getSubtotal()))
                .toList();
        return new OrderResponse(o.getId(), o.getCreatedAt(), o.getTotal(), items);
    }
}
