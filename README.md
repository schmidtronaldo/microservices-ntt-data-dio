# Microservices Demo (Spring Boot 3.3, Spring Cloud 2024, Java 21)

Arquitetura:
- **Service Discovery (Eureka)**: `localhost:8761`
- **API Gateway**: `localhost:8700`
- **Product Service**: porta 8100 (acessado via gateway)
- **Order Service**: porta 8200 (acessado via gateway)

Todos os endpoints devem ser acessados **via API Gateway** e com **token** no header Authorization:
```
Authorization: Bearer SECRET-TOKEN-123
```

## Como executar (em 3 terminais):

1. **Service Discovery**
```bash
mvn -q -pl service-discovery spring-boot:run
```

2. **Product & Order Services** (cada um em um terminal)
```bash
mvn -q -pl product-service spring-boot:run
mvn -q -pl order-service spring-boot:run
```

3. **API Gateway**
```bash
mvn -q -pl api-gateway spring-boot:run
```

Ou inicie tudo com:
```bash
mvn -q -T 1C -am -pl service-discovery,api-gateway,product-service,order-service spring-boot:run
```

## Testando via Gateway

### Criar produto
```bash
curl -i -X POST http://localhost:8700/produtos  -H "Authorization: Bearer SECRET-TOKEN-123"  -H "Content-Type: application/json"  -d '{ "nome": "Café", "descricao": "Café torrado e moído", "preco": 19.90 }'
```

### Listar produtos
```bash
curl -s http://localhost:8700/produtos -H "Authorization: Bearer SECRET-TOKEN-123" | jq .
```

### Criar pedido (com base em produtos existentes)
```bash
curl -i -X POST http://localhost:8700/pedidos  -H "Authorization: Bearer SECRET-TOKEN-123"  -H "Content-Type: application/json"  -d '{ "itens": [ { "productId": 1, "quantidade": 2 }, { "productId": 1, "quantidade": 1 } ] }'
```

### Listar pedidos
```bash
curl -s http://localhost:8700/pedidos -H "Authorization: Bearer SECRET-TOKEN-123" | jq .
```

## Notas
- O Gateway valida o token **antes** de rotear a requisição.
- Os serviços registram-se no Eureka e o Gateway roteia por **serviceId** (`lb://product-service`, `lb://order-service`).
- Persistência com **H2 (memória)** em ambos os serviços.
- Boas práticas REST: recursos em plural, ids nos paths, códigos HTTP adequados, validação de payloads.
