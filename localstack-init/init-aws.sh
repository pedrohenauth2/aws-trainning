#!/bin/bash

echo "Inicializando recursos AWS no LocalStack..."

# Criar tabela DynamoDB
awslocal dynamodb create-table \
  --table-name Pedidos \
  --attribute-definitions AttributeName=pedidoId,AttributeType=S \
  --key-schema AttributeName=pedidoId,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST

echo "✅ Tabela DynamoDB 'Pedidos' criada"

# Criar bucket S3
awslocal s3 mb s3://aws-trainning-bucket

echo "✅ Bucket S3 'aws-trainning-bucket' criado"

# Criar topic SNS
awslocal sns create-topic --name aws-trainning

echo "✅ Topic SNS 'aws-trainning' criado"

# Criar fila SQS
awslocal sqs create-queue --queue-name aws-trainning-queue

echo "✅ Fila SQS 'aws-trainning-queue' criada"

# Criar subscription SNS -> SQS
awslocal sns subscribe \
  --topic-arn arn:aws:sns:us-east-1:000000000000:aws-trainning \
  --protocol sqs \
  --notification-endpoint arn:aws:sqs:us-east-1:000000000000:aws-trainning-queue

echo "✅ Subscription SNS->SQS criada"

# Criar função Lambda
cd /tmp
echo 'import json

def handler(event, context):
    if isinstance(event, str):
        event = json.loads(event)
    numero = event.get("numero", 0)
    resultado = numero * 2
    return {"statusCode": 200, "resultado": resultado}' > lambda_function.py

zip function.zip lambda_function.py

awslocal lambda create-function \
  --function-name minha-lambda \
  --runtime python3.11 \
  --handler lambda_function.handler \
  --role arn:aws:iam::000000000000:role/lambda-role \
  --zip-file fileb://function.zip

echo "✅ Função Lambda 'minha-lambda' criada"

echo "🎉 Todos os recursos AWS foram criados com sucesso!"
