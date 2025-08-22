package com.example.product.web;

import com.example.product.domain.Product;
import com.example.product.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto) {
        Product saved = repo.save(new Product(dto.nome(), dto.descricao(), dto.preco()));
        return ResponseEntity.created(URI.create("/produtos/" + saved.getId()))
                .body(toDto(saved));
    }

    @GetMapping
    public List<ProductDTO> list() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        return repo.findById(id).map(p -> ResponseEntity.ok(toDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return repo.findById(id).map(p -> {
            p.setNome(dto.nome());
            p.setDescricao(dto.descricao());
            p.setPreco(dto.preco());
            Product saved = repo.save(p);
            return ResponseEntity.ok(toDto(saved));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProductDTO toDto(Product p) {
        return new ProductDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco());
    }
}
