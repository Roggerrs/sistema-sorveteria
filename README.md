
---

# 📦 README — Backend

👉 `sistema-sorveteria`

```markdown
# 🍨 Sistema de Sorveteria — Backend

Backend do sistema de sorveteria desenvolvido em **Java com Spring Boot**, responsável por gerenciar pedidos, sorvetes, atendentes e relatórios de vendas.

O projeto utiliza **arquitetura em camadas**, **JPA/Hibernate**, **banco H2** e expõe uma **API REST** consumida pelo frontend em React.

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
- Banco H2 (memória)
- Maven
- Swagger (documentação da API)

---

## 🧩 Funcionalidades

- Cadastro e listagem de atendentes
- Criação de pedidos
- Adição de sorvetes ao pedido
- Cálculo automático de valores
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

- Banco: **H2**
- Console disponível em:


```

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

````

Configuração padrão:
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: `sa`
- Senha: (vazia)

---

# 🔥 **MODELO VISUAL (ER SIMPLIFICADO)**

```
ATENDENTE (1) ----< (N) PEDIDO (1) ----< (N) SORVETE >---- (1) TAMANHO
                                   |
                                   V
                             (N) SORVETE_has_SABOR >---- (1) SABOR
```


---

## ▶️ Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/Roggerrs/sistema-sorveteria
````

2. Importe em sua IDE (IntelliJ / Eclipse)

3. Execute a aplicação:

```bash
mvn spring-boot:run
```

4. A API estará disponível em:

```
http://localhost:8080
```

---

## 📑 Documentação da API (Swagger)

```
http://localhost:8080/swagger-ui.html
```

---

## 🔗 Projetos Relacionados

* Frontend React:
  [https://github.com/Roggerrs/sorveteria-frontend](https://github.com/Roggerrs/sorveteria-frontend)

* Modelagem e SQL do banco:
  [https://github.com/Roggerrs/Sistema-Sorveteria-SQL](https://github.com/Roggerrs/Sistema-Sorveteria-SQL)

````

---

# 🎨 README — Frontend  
👉 `sorveteria-frontend`

```markdown
# 🍦 Sistema de Sorveteria — Frontend

Frontend do sistema de sorveteria desenvolvido em **React**, consumindo uma **API REST em Spring Boot**.

A interface foi construída sem frameworks CSS, utilizando apenas **CSS puro**, com foco em organização, legibilidade e identidade visual.

---

## 🎯 Objetivo

Fornecer uma interface simples e funcional para:

- Selecionar atendente
- Criar pedidos
- Montar sorvetes (tamanho + sabores)
- Visualizar pedidos
- Consultar relatórios de vendas

---

## 🛠️ Tecnologias Utilizadas

- React
- Vite
- React Router DOM
- JavaScript (ES6+)
- CSS puro (Flexbox)

---

## 📂 Estrutura do Projeto

````
📑 Endpoints da API (CRUD)

A API expõe endpoints REST organizados por recurso, permitindo operações de criação, consulta e atualização conforme a regra de negócio do sistema.

---
👤 Atendentes

| Método | Endpoint                    | Descrição                        |
| ------ | --------------------------- | -------------------------------- |
| `POST` | `/atendentes`               | Cadastra um novo atendente       |
| `GET`  | `/atendentes`               | Lista todos os atendentes ativos |
| `PUT`  | `/atendentes/{id}/inativar` | Inativa um atendente             |

---
🧾 Pedidos

| Método | Endpoint                 | Descrição                                    |
| ------ | ------------------------ | -------------------------------------------- |
| `POST` | `/pedidos`               | Cria um novo pedido vinculado a um atendente |
| `GET`  | `/pedidos`               | Lista todos os pedidos                       |
| `GET`  | `/pedidos/{id}`          | Consulta os detalhes de um pedido            |
| `PUT`  | `/pedidos/{id}/inativar` | Inativa um pedido                            |

---
🍦 Sorvetes (Itens do Pedido)

Os sorvetes fazem parte do pedido e são criados dentro do fluxo de criação do pedido, contendo:

* Tamanho

* Um ou mais sabores

* Valor calculado automaticamente
---
📏 Tamanhos

| Método | Endpoint    | Descrição                                |
| ------ | ----------- | ---------------------------------------- |
| `GET`  | `/tamanhos` | Lista os tamanhos disponíveis de sorvete |

---

🍫 Sabores

| Método | Endpoint   | Descrição                    |
| ------ | ---------- | ---------------------------- |
| `GET`  | `/sabores` | Lista os sabores disponíveis |

---
📊 Relatórios

Os relatórios fornecem dados consolidados para análise de venda

| Método | Endpoint                             | Descrição                              |
| ------ | ------------------------------------ | -------------------------------------- |
| `GET`  | `/relatorios/total-faturado`         | Retorna o total faturado               |
| `GET`  | `/relatorios/por-atendente`          | Retorna o total faturado por atendente |
| `GET`  | `/relatorios/sabores-mais-vendidos`  | Retorna os sabores mais vendidos       |
| `GET`  | `/relatorios/tamanhos-mais-vendidos` | Retorna os tamanhos mais vendidos      |

```
src
├─ api
│   └─ api.js
├─ pages
│   ├─ SelecionarAtendente.jsx
│   ├─ CriarPedido.jsx
│   ├─ CriarSorvete.jsx
│   ├─ ListarPedidos.jsx
│   ├─ PedidoDetalhe.jsx
│   └─ Relatorios.jsx
├─ App.jsx
├─ main.jsx
└─ style.css

```

---

## 🎨 Design

- Tema escuro
- Cores quentes (laranja/amarelo)
- Botões padronizados
- Layout organizado com Flexbox
- Interface pensada para sistemas administrativos

---

## 🔗 Integração com Backend

O frontend consome a API rodando em:

```

[http://localhost:8080](http://localhost:8080)

````

Certifique-se de que o backend esteja em execução antes de iniciar o frontend.

---

## ▶️ Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/Roggerrs/sorveteria-frontend
````

2. Instale as dependências:

```bash
npm install
```

3. Execute o projeto:

```bash
npm run dev
```

4. Acesse no navegador:

```
http://localhost:5173
```

---

## 📊 Telas Disponíveis

* Seleção de Atendente
* Criação de Pedido
* Adição de Sorvetes
* Listagem de Pedidos
* Detalhes do Pedido
* Relatórios de Vendas

---

## 🔗 Projetos Relacionados

* Backend Spring Boot:
  [https://github.com/Roggerrs/sistema-sorveteria](https://github.com/Roggerrs/sistema-sorveteria)

* SQL e modelagem do banco:
  [https://github.com/Roggerrs/Sistema-Sorveteria-SQL](https://github.com/Roggerrs/Sistema-Sorveteria-SQL)

````

---

# 🗄️ README — SQL / Modelagem  
👉 `Sistema-Sorveteria-SQL`

```markdown
# 🗄️ Sistema de Sorveteria — SQL e Modelagem

Repositório contendo a **modelagem do banco de dados**, scripts SQL e consultas utilizadas no projeto Sistema de Sorveteria.

Este repositório representa a **fase inicial do projeto**, onde a entidade principal era **Cliente**, posteriormente substituída por **Atendente** durante a evolução da regra de negócio.

---

## 📌 Observação Importante

⚠️ Este repositório é **histórico**.

A versão final do sistema utiliza:
- Entidade **Atendente**
- Banco H2
- JPA/Hibernate

---

## 📊 Conteúdo

- Scripts de criação de tabelas
- Consultas SQL
- Relatórios em SQL puro
- Normalização do banco
- Relacionamentos e cardinalidades

---

## 🔄 Evolução do Projeto

- Cliente → Atendente
- SQL puro → JPA + Hibernate
- Queries SQL → Projections
- Banco físico → H2 em memória

---

## 🔗 Projetos Atuais

- Backend atualizado:  
  https://github.com/Roggerrs/sistema-sorveteria

- Frontend React:  
  https://github.com/Roggerrs/sorveteria-frontend
````

## 📢 Tags

`Java` `Spring Boot` `API REST` `JPA` `Hibernate`  
`SQL` `H2` `Arquitetura em Camadas`  
`DTO` `Projections` `Swagger`  
`Git` `GitHub`

---