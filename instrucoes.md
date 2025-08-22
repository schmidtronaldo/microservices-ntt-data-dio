
# Microservices Spring Cloud Demo

Este projeto é uma aplicação de **microserviços com Spring Boot e Spring Cloud** que demonstra conceitos modernos de arquitetura distribuída:  
- Service Discovery (Eureka)  
- API Gateway (Spring Cloud Gateway)  
- Comunicação entre microserviços via REST  
- Persistência com banco de dados em memória (H2)  
- Autenticação simples via **token fixo** validado pelo gateway  
- Docker e Docker Compose para orquestração  

---

## 🚀 Arquitetura

![Arquitetura](docs/architecture.png)

**Componentes principais:**
- **Service Discovery** (`service-discovery`): Eureka Server para registro e descoberta de serviços.  
- **API Gateway** (`api-gateway`): Porta de entrada única, valida token e roteia requisições para os serviços.  
- **Product Service** (`product-service`):  
  - CRUD de produtos (nome, descrição, preço).  
  - Persistência em **H2 Database**.  
- **Order Service** (`order-service`):  
  - Consulta produtos via `product-service`.  
  - Permite simular criação de pedidos com base em produtos cadastrados.  

**Fluxo:**  
`Browser → API Gateway → Service Discovery → Product/Order Services`

---

## ⚙️ Tecnologias

- **Java 21**  
- **Spring Boot 3**  
- **Spring Cloud Netflix Eureka**  
- **Spring Cloud Gateway**  
- **Spring Security** (validação de token)  
- **H2 Database**  
- **Maven**  
- **Docker & Docker Compose**  

---

## 📂 Estrutura do projeto

```

microservices-spring-cloud-demo/
│── api-gateway/
│── order-service/
│── product-service/
│── service-discovery/
│── docker-compose.yml
│── .dockerignore
│── README.md

```

---

## 🔑 Autenticação

- Todas as requisições ao **API Gateway** exigem o header:
```

Authorization: Bearer SECRET-TOKEN-123

````
- O token pode ser configurado via variável de ambiente (`security.token`) no `docker-compose.yml`.

---

## 🐳 Docker & Compose

### Construção e execução
Na raiz do projeto:

```bash
docker compose up --build
````

### Serviços expostos

| Serviço           | Porta local | URL                                                                           |
| ----------------- | ----------- | ----------------------------------------------------------------------------- |
| **Eureka Server** | 8761        | [http://localhost:8761](http://localhost:8761)                                |
| **API Gateway**   | 8700        | [http://localhost:8700](http://localhost:8700)                                |
| Product Service   | 8100        | via gateway: [http://localhost:8700/produtos](http://localhost:8700/produtos) |
| Order Service     | 8200        | via gateway: [http://localhost:8700/pedidos](http://localhost:8700/pedidos)   |

---

## 📡 Exemplos de uso

### 1. Cadastrar produto

```bash
curl -X POST http://localhost:8700/produtos \
  -H "Authorization: Bearer SECRET-TOKEN-123" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Notebook","descricao":"Dell i7","preco":4500.0}'
```

### 2. Listar produtos

```bash
curl -X GET http://localhost:8700/produtos \
  -H "Authorization: Bearer SECRET-TOKEN-123"
```

### 3. Criar pedido

```bash
curl -X POST http://localhost:8700/pedidos \
  -H "Authorization: Bearer SECRET-TOKEN-123" \
  -H "Content-Type: application/json" \
  -d '{"produtos":[1,2]}'
```

---

## 🛠️ Desenvolvimento local (sem Docker)

Cada microserviço pode ser iniciado individualmente via Maven:

```bash
cd service-discovery && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
```

---

## 🔮 Melhorias futuras

* Persistência real com PostgreSQL.
* Config Server para centralizar configurações.
* Implementação de **circuit breaker** (Resilience4j).
* Observabilidade com Spring Boot Actuator + Prometheus + Grafana.
* Autenticação baseada em **JWT** ou OAuth2.

---

## 👨‍💻 Autor

Projeto desenvolvido como exemplo de arquitetura de **microserviços com Spring Boot + Spring Cloud**.

```



