package aws.training.AWS_TRAINING.lambda.controller;

import aws.training.AWS_TRAINING.lambda.controller.request.LambdaRequest;
import aws.training.AWS_TRAINING.lambda.service.LambdaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lambda")
@RestController
@RequestMapping("/lambda")
public class LambdaController {

    private final LambdaService lambdaService;

    public LambdaController(LambdaService lambdaService) {
        this.lambdaService = lambdaService;
    }

    @Operation(summary = "Invocar função Lambda")
    @PostMapping("/invocar")
    public ResponseEntity<String> invocar(@RequestBody LambdaRequest request) {
        String resultado = lambdaService.invocar(request);
        return ResponseEntity.ok(resultado);
    }
}
