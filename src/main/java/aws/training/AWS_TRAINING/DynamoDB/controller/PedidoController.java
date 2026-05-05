package aws.training.AWS_TRAINING.DynamoDB.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aws.training.AWS_TRAINING.DynamoDB.model.Pedido;
import aws.training.AWS_TRAINING.DynamoDB.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedidos - DynamoDB")
@RestController
@RequestMapping("/pedidos/dynamoDB")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.criar(pedido));
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable String pedidoId) {
        Pedido pedido = pedidoService.buscarPorId(pedidoId);
        if (pedido == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> deletar(@PathVariable String pedidoId) {
        pedidoService.deletar(pedidoId);
        return ResponseEntity.noContent().build();
    }
}
