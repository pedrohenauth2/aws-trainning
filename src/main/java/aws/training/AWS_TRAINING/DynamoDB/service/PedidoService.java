package aws.training.AWS_TRAINING.dynamodb.service;

import aws.training.AWS_TRAINING.dynamodb.model.Pedido;
import aws.training.AWS_TRAINING.dynamodb.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criar(Pedido pedido) {
        // gera um ID único para o pedido
        pedido.setPedidoId(UUID.randomUUID().toString());
        pedidoRepository.save(pedido);
        return pedido;
    }

    public Pedido buscarPorId(String pedidoId) {
        return pedidoRepository.findById(pedidoId);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public void deletar(String pedidoId) {
        pedidoRepository.delete(pedidoId);
    }
}
