# Desafio Técnico Backend PL KLAB - Projeto JAVA

## Sobre o Projeto

### Desenvolvimento

O desafio proposto consiste em criar um baralho utilizando a API disponível no final deste documento e montar quatro mãos, cada uma com 5 cartas, verificando qual mão possui a maior somatória de valores. Em caso de empate, deve-se retornar apenas os vencedores empatados.

#### Regras:
- A = 1
- K = 13
- Q = 12
- J = 11

**Exemplo:**
- Jogador 1 = [A, 2, 3, 4, 5]
- Jogador 2 = [K, Q, J, 10, 9]
- Jogador 3 = [8, 9, 2, A, J]
- Jogador 4 = [2, 2, 5, 7, 2]

Vencedor: Jogador 2 com 55 pontos

## Documentação da API

A documentação completa da API pode ser encontrada [aqui](http://localhost:8081/swagger-ui/index.html#/).

# Como Executar o Projeto

### Configuração do Banco de Dados

Este projeto já está previamente configurado para utilizar o PostgreSQL como banco de dados padrão. Se desejar mudar para o H2, siga estas etapas:

1. Abra o arquivo `application.properties`.
2. Altere a propriedade `spring.profiles.active` de `dev` para `test`.
3. Reinicie o aplicativo para aplicar as alterações.

**Nota**: Certifique-se de ter o PostgreSQL instalado e configurado corretamente antes de usar o perfil `dev`. Além disso, crie um banco de dados chamado `klabdb`.

### Pré-requisitos:
- Java 17

### Sobre as Dependências da API:

- Utiliza Spring Boot como framework principal.
- Usa Spring Boot Starter Data JPA para persistência de dados.
- Inclui Spring Boot Starter Web para desenvolvimento de aplicativos web.
- Utiliza Spring Boot DevTools para facilitar o desenvolvimento em tempo de execução.
- Usa Gson para processamento de JSON.
- Utiliza o banco de dados H2 em tempo de execução.
- Utiliza o driver JDBC do PostgreSQL para integração com o banco de dados PostgreSQL.
- Inclui a biblioteca Springdoc OpenAPI para documentação da API com Swagger UI.
- Usa Lombok para reduzir a verbosidade do código Java.
- Inclui Spring Boot Starter Test para testes automatizados.

## Autor
Antonio Igor Ribeiro de Sousa

LinkedIn: [Clique aqui!](https://www.linkedin.com/in/igor-ribeiro-sousa/)

GitHub: [Clique aqui!](https://github.com/igor-ribeiro-sousa)
