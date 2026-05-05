import json

def handler(event, context):
    numero = event.get("numero", 0)
    resultado = numero * 2
    return {
        "statusCode": 200,
        "resultado": resultado
    }
