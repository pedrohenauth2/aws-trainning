# AWS Training

Projeto Spring Boot para estudo prático dos principais serviços da AWS usando **LocalStack** como emulador local.

## Pré-requisitos

- Java 17+
- Maven
- Docker Desktop

## Serviços implementados

| Serviço | Descrição | Endpoints |
|---|---|---|
| **SNS** | Publicação de mensagens em tópico | `POST /sns/publish` |
| **SQS** | Consumo de mensagens da fila | (consumer automático a cada 5s) |
| **DynamoDB** | CRUD de Pedidos | `POST/GET/DELETE /pedidos/dynamoDB` |
| **S3** | Upload, download e listagem de arquivos | `POST/GET/DELETE /s3/*` |
| **Lambda** | Invocação de função serverless | `POST /lambda/invocar` |

## Como subir

### 1. Subir o LocalStack

```bash
docker-compose up -d
```

O LocalStack sobe na porta `4566` e executa automaticamente o script `localstack-init/init-aws.sh` que cria todos os recursos necessários:

- Tabela DynamoDB `Pedidos`
- Bucket S3 `aws-trainning-bucket`
- Topic SNS `aws-trainning`
- Fila SQS `aws-trainning-queue`
- Subscription SNS → SQS
- Função Lambda `minha-lambda`

Aguarde o container ficar `healthy` antes de subir a aplicação:

```bash
docker ps --filter "name=localstack"
```

### 2. Subir a aplicação Spring Boot

```bash
./mvnw spring-boot:run
```

A aplicação sobe na porta `8080`.

### 3. Acessar o Swagger

```
http://localhost:8080/swagger-ui/index.html
```

## Exemplos de uso

### SNS — Publicar mensagem
```bash
curl -X POST http://localhost:8080/sns/publish \
  -H "Content-Type: application/json" \
  -d '"Minha mensagem de teste"'
```

### DynamoDB — Criar pedido
```bash
curl -X POST http://localhost:8080/pedidos/dynamoDB \
  -H "Content-Type: application/json" \
  -d '{"clienteId": "pedro", "status": "pendente", "valor": 150.0}'
```

### DynamoDB — Listar pedidos
```bash
curl http://localhost:8080/pedidos/dynamoDB
```

### S3 — Upload de arquivo
```bash
curl -X POST http://localhost:8080/s3/upload \
  -F "file=@/caminho/para/arquivo.pdf"
```

### S3 — Listar arquivos
```bash
curl http://localhost:8080/s3/listar
```

### Lambda — Invocar função
```bash
curl -X POST http://localhost:8080/lambda/invocar \
  -H "Content-Type: application/json" \
  -d '{"numero": 5}'
```

## Observação sobre o LocalStack

O LocalStack **não persiste dados** entre reinicializações por padrão. Ao reiniciar o container, todos os recursos são recriados automaticamente pelo script de init, mas os dados inseridos (pedidos, arquivos, etc.) são perdidos.

Para persistir dados, o volume `localstack-data` está configurado no `docker-compose.yml`.
