# gbx-challenge-java
Desafio Backend Java para GBX do Brasil

## Microservico de Transações

### Ideia inicial
Desenhe e codifique um microserviço que será responsável pelas execuções e persistência das transações financeiras requisitadas. Deverá ser uma API Rest.

- Endpoints: adicionar usuário / listar usuários / criar transação / consultar transações.
- Siga o approach simplificado de Controller, Service, Repository para facilidade de leitura e organização.
- Utilize tabelas relacionais com o banco de dados de sua preferência para persistência.
- Cada usuário terá um conta corrente, que será um registro na tabela usuarios (id, nome, número da conta e saldo).
- Persista cada transação como um registro na tabela transações, com conta de origem, conta de destino, valor e data da transação.
- Execute um número X de transações entre diferentes usuários - onde X > 10.

Sinta-se livre para aprofundar a implementação do seu serviço, conforme o seu conhecimento, com tratamento de exceções, testes, entre outros itens que julgar conveniente.

### Tecnologias
- Java 11+
- Spring Boot
- SQL