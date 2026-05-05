package aws.training.AWS_TRAINING.DynamoDB.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Pedido {

    @Getter(onMethod_ = {@DynamoDbPartitionKey})
    private String pedidoId;

    private String clienteId;
    private String status;
    private Double valor;
}
