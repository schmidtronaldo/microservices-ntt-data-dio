# Bootcamp NTT-DATA-Java para iniciantes
# Plataforma: Digital Innovation One 
# Apoio: NTT-DATA

# Desafio Final : NTT-DATA

# Instruçoes
Desenvolver uma aplicaçao baseada em microserviços utilizando Spring boot e Spring cloud aplicando os conceitos de arquitetura moderna com service discovery,api gateway,comunicaçao entre serviços alem de aplicar persistencia de dados e boas praticas Rest.

# Arquitetura proposta :
- Criar 2 microserviços que se comunicam entre si e H2database,Service Discovery e Api Gateway
- Service Discovery se comunica com Api Gateway
- Browser se comunica com api gateway
# Contexto do negocio:
- Desenvolver um pequeno sistema de gestão de pedidos com catalogo de produtos
> microserviço1:
- catalogo de produtos: cadastra,lista,consulta produtos(nome,descriçao,preço)persistencia de dados via h2database
> microserviço2(simula pedidos):
- realiza chamadas microserviço1 para buscar produtos disponiveis,permite simular a criação de um pedido com base em lista de produtos

# Requisitos tecnicos:
- Conter dois microserviços independentes,spring boot em todos os serviços,spring cloud eureka como service discovery,spring cloud gateway como API gateway,Rest apis como boas praticas.
- Garantir que: microserviço1 acessivel por produtos,microserviço2 acesivel por pedidos.Todos os endpoints devem ser acessados via gateway.

# Portas:
- microserviço1:produtos: 8100-8199
- microserviço2: pedidos: 8200-8299
- api gateway: 8700-8799

# Extras:
- Implementar autenticaçoa simplificada com spring security,token fixo no header authorization,api gateway valida o token antes de realizar a requisiçao.
- Pode usar um filter simples ou um AuthenticationManager customizado.

# Resumo do que você quer fazer:

* **Arquitetura:** Microserviços com Spring Boot + Spring Cloud.
* **Componentes:**

    1. **Service Discovery:** Spring Cloud Eureka.
    2. **API Gateway:** Spring Cloud Gateway.
    3. **Microserviço 1:** Catálogo de produtos (H2 DB, CRUD básico).
    4. **Microserviço 2:** Pedidos (consulta produtos via microserviço 1, criação de pedidos simulada).
* **Comunicação:**

    * Browser → API Gateway → microserviços
    * Microserviço 2 → Microserviço 1 (via REST)
    * Service Discovery → API Gateway → microserviços
* **Segurança:** Token fixo no header Authorization, validado pelo API Gateway.
* **Portas:**

    * Produtos: 8100-8199
    * Pedidos: 8200-8299
    * Gateway: 8700-8799
* **Extras:** Boas práticas REST, autenticação simplificada, persistência H2, Maven, Docker/Docker-Compose, Java 21.

O que vamos fazer agora é planejar **etapa por etapa* :

1. **Planejamento da estrutura de pastas e projetos** (definir projetos Maven independentes + pom.xml raiz).
2. **Configuração do Service Discovery (Eureka Server)**.
3. **Criação do microserviço 1 (Catálogo de Produtos)** com H2 DB e endpoints REST.
4. **Criação do microserviço 2 (Pedidos)** com integração REST para microserviço 1.
5. **Configuração do API Gateway** com roteamento e filtro de autenticação.
6. **Testes locais de cada serviço via API Gateway**.
7. **Dockerização dos microserviços e docker-compose** para orquestração.

## Observações importantes:
- faça um fork deste repositorio em sua maquina.
- crie uma nova branch 
- Utilize a IDE Intellij de preferencia
- Rode o seguinte comando:
   - docker-compose up --build
Pode ocorrer alguns erros de build:
   - Verifique se possui Docker e docker-compose instalados: 
     - docker --version docker-compose --version
   - Verifique As imagens do conatiner em execução: 
     - sudo docker ps -aq
     - remova todos os containeres não utilizados:
       - sudo docker system prune 
Qualquer duvida envia uma pull request ou comentario.
Obrigado.
