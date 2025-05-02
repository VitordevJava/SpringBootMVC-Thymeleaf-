
# 🧑‍💼 Spring Boot MVC com Thymeleaf

Projeto de gerenciamento de pessoas com cadastro, criado para fins de aprendizado e prática com as tecnologias **Java Spring Boot**, **Thymeleaf** e **PostgreSQL**.

---

## 📋 Descrição

Este sistema web permite o **cadastro, visualização, edição e exclusão de pessoas**, servindo como um controle administrativo para gerentes ou responsáveis.  
Ele utiliza a arquitetura MVC, com renderização de páginas via Thymeleaf e persistência dos dados em banco PostgreSQL.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
  - Spring Web
  - Spring Data JPA
- Thymeleaf
- PostgreSQL
- Maven

---

## 💾 Como Rodar o Projeto Localmente

### Pré-requisitos

- Java 17 ou superior
- Maven instalado
- PostgreSQL instalado e em execução

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/VitordevJava/SpringBootMVC-Thymeleaf-.git
   cd SpringBootMVC-Thymeleaf-
   ```

2. **Configure o banco de dados PostgreSQL:**

   Crie um banco chamado `peoplemanager` e ajuste o `application.properties`:

   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/peoplemanager
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse no navegador:**
   ```
   http://localhost:8080
   ```

---

## 📌 Funcionalidades

- 📥 Cadastro de pessoas
- 📝 Edição de dados
- ❌ Exclusão de registros
- 📄 Listagem com visualização

---

## 🗂 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.example.demo
│   │       ├── controller
│   │       ├── model
│   │       ├── repository
│   │       └── DemoApplication.java
│   └── resources
│       ├── static
│       ├── templates
│       │   └── pessoas.html
│       └── application.properties
```

---

## 🤝 Contribuições

Este projeto foi feito para fins de estudo e está aberto a melhorias.  
Sinta-se à vontade para abrir issues ou pull requests com sugestões e correções.

---

## 📎 Autor

**Vitor**  
GitHub: [@VitordevJava](https://github.com/VitordevJava)

