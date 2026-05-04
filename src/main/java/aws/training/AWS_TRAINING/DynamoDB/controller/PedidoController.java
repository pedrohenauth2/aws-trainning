package aws.training.AWS_TRAINING.dynamodb.controller;

import aws.training.AWS_TRAINING.dynamodb.model.Pedido;
import aws.training.AWS_TRAINING.dynamodb.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
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
