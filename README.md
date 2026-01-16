
---

```markdown
# 🍨 Sistema de Sorveteria — Backend

Backend do sistema de sorveteria desenvolvido em **Java com Spring Boot**, responsável por gerenciar pedidos, sorvetes, atendentes e relatórios de vendas.

O projeto utiliza **arquitetura em camadas**, **JPA/Hibernate** e expõe uma **API REST** consumida por um frontend em React.

Atualmente, o backend encontra-se **online em ambiente de produção**, publicado em **servidor cloud (Railway)**, integrado a um **banco de dados PostgreSQL**.  
O frontend da aplicação está publicado separadamente em **servidor cloud (Vercel)**, consumindo esta API em tempo real.

---

## 🧠 Contexto do Projeto

Inicialmente o sistema foi modelado com a entidade **Cliente**, porém durante a evolução do projeto a regra de negócio foi alterada para **Atendente**, representando o funcionário responsável pelo pedido.

Essa mudança impactou:
- Entidades
- Relacionamentos
- Relatórios
- Fluxo do pedido

---

## 🏗️ Arquitetura

O backend segue o padrão:

```

Controller → Service → Repository → Database

```

Estrutura principal:

```
```
src/main/java
├─ controller
├─ service
├─ repository
├─ domain
│   ├─ entity
│   └─ dto
└─ exception

```

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger (documentação da API)
- Deploy em servidor cloud (**Railway**)

---

## 🧩 Funcionalidades

- Cadastro e listagem de atendentes
- Criação de pedidos
- Adição de sorvetes ao pedido
- Cálculo automático de valores
- Inativação lógica de pedidos e atendentes
- Relatórios:
  - Total faturado
  - Total por atendente
  - Sabores mais vendidos
  - Tamanhos mais vendidos

---

## 📊 Relatórios

Os relatórios são gerados via **queries SQL nativas** utilizando **projections**, garantindo:
- Melhor performance
- Código limpo
- Separação entre entidade e retorno de dados

---

## 🗄️ Banco de Dados

O backend utiliza **PostgreSQL** como banco de dados relacional em produção.

Características do banco:

- Banco hospedado em ambiente cloud
- Persistência real de dados
- Integração direta com o backend via **JPA/Hibernate**
- Relacionamentos normalizados entre entidades
- Queries SQL nativas para relatórios

O banco de dados encontra-se conectado diretamente ao servidor backend em produção.

---

# 🔥 **MODELO VISUAL (ER SIMPLIFICADO)**

```

ATENDENTE (1) ----< (N) PEDIDO (1) ----< (N) SORVETE >---- (1) TAMANHO
|
V
(N) SORVETE_has_SABOR >---- (1) SABOR

````

---

## ▶️ Como Executar

### Execução local (desenvolvimento)

1. Clone o repositório:
```bash
git clone https://github.com/Roggerrs/sistema-sorveteria
````

2. Importe em sua IDE (IntelliJ / Eclipse)

3. Configure as variáveis de ambiente do banco de dados

4. Execute a aplicação:

```bash
mvn spring-boot:run
```

---

## 📑 Documentação da API (Swagger)

A API possui documentação gerada automaticamente com **Swagger/OpenAPI**, facilitando:

* Visualização dos endpoints
* Validação dos contratos
* Testes durante o desenvolvimento

---

## 🔗 Projetos Relacionados

* Frontend React:
  [https://github.com/Roggerrs/sorveteria-frontend](https://github.com/Roggerrs/sorveteria-frontend)

* Modelagem e SQL do banco:
  [https://github.com/Roggerrs/Sistema-Sorveteria-SQL](https://github.com/Roggerrs/Sistema-Sorveteria-SQL)

---

## ✅ Status do Projeto

✔ API funcional
✔ Arquitetura em camadas
✔ Backend online em produção
✔ Banco PostgreSQL integrado
✔ Servidor cloud (Railway)
✔ Frontend em produção (Vercel)
✔ Comunicação frontend ↔ backend
✔ Pronto para portfólio

```
