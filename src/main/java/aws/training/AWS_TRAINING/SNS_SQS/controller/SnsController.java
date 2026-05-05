package aws.training.AWS_TRAINING.SNS_SQS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aws.training.AWS_TRAINING.SNS_SQS.service.sns.SnsPublisherService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SNS")
@RestController
@RequestMapping("/sns")
public class SnsController {

    private final SnsPublisherService snsPublisherService;

    public SnsController(SnsPublisherService snsPublisherService) {
        this.snsPublisherService = snsPublisherService;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody String message) {
        String messageId = snsPublisherService.publish(message);
        return ResponseEntity.ok("Mensagem publicada! ID: " + messageId);
    }
}
