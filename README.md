# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações 
CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks).

## Métodos
Requisições para a API devem seguir os padrões:

| Método   | Descrição                                 |
|----------|-------------------------------------------|
| `GET`    | Retorna informações de uma ou mais ações. |
| `POST`   | Utilizado para criar uma nova ação.       |
| `PUT`    | Atualiza dados de uma ação.               |
| `DELETE` | Remove uma ação do sistema.               |

### Dados para envio nas rotas POST e PUT

| Parâmetro     | Descrição                                              |
|---------------|--------------------------------------------------------|
| `symbol`      | Símbolo da ação contendo 3 letras seguido de 1 número. |
| `companyName` | Nome da empresa que a ação pertence.                   |
| `price`       | Um valor positivo representando o preço da ação.       |

## Respostas

| Código | Descrição                                                      |
|--------|----------------------------------------------------------------|
| `200`  | Requisição executada com sucesso (success).                    |
| `201`  | Solicitação foi bem-sucedida e resultou na criação de uma ação. |
| `204`  | Solicitação foi bem-sucedida e resultou na deleção de uma ação. |
| `404`  | Registro pesquisado não encontrado (Not found).                |
| `500`  | Erro na validação dos dados - Dados inválidos                  |




## Recursos


### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis.

**Endpoint:**
```http
GET /stocks
```

### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
GET /stocks/{id}
```

### Criar uma Nova Ação
Cria uma nova ação com base nos dados fornecidos.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBS3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}

```
### Atualizar uma Ação por ID
Atualiza os detalhes de uma ação específica com base no ID.

**Endpoint:**
```http
PUT /stocks/{id}
```
**Corpo da Solicitação (Request Body):**

```JSON
{
  "symbol": "BBS3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97
}

```

### Excluir uma Ação por ID
Exclui uma ação específica com base no ID.

**Endpoint:**
```http
DELETE /stocks/{id}
```


## Uso
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos
- Java 11 ou superior
- Maven
- Banco de dados

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Contribuições
Contribuições são bem-vindas!

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).

