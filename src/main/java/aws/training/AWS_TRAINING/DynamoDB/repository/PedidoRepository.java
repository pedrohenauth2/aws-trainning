package aws.training.AWS_TRAINING.dynamodb.repository;

import aws.training.AWS_TRAINING.dynamodb.model.Pedido;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PedidoRepository {

    private final DynamoDbTable<Pedido> table;

    public PedidoRepository(DynamoDbEnhancedClient enhancedClient) {
        // mapeia a classe Pedido para a tabela "Pedidos" do DynamoDB
        this.table = enhancedClient.table("Pedidos", TableSchema.fromBean(Pedido.class));
    }

    public void save(Pedido pedido) {
        table.putItem(pedido);
    }

    public Pedido findById(String pedidoId) {
        Key key = Key.builder().partitionValue(pedidoId).build();
        return table.getItem(key);
    }

    public List<Pedido> findAll() {
        return table.scan().items().stream().collect(Collectors.toList());
    }

    public void delete(String pedidoId) {
        Key key = Key.builder().partitionValue(pedidoId).build();
        table.deleteItem(key);
    }
}
