package aws.training.AWS_TRAINING.SNS_SQS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class SqsConsumerService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    public SqsConsumerService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    // Executa a cada 5 segundos verificando se há mensagens na fila
    @Scheduled(fixedDelay = 5000)
    public void consume() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)   // busca 1 mensagem por vez
                .waitTimeSeconds(2)       // long polling: espera até 2s por mensagens
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        if (messages.isEmpty()) return;

        Message message = messages.get(0);
        System.out.println("📨 Mensagem recebida da fila SQS:");
        System.out.println("   ID: " + message.messageId());
        System.out.println("   Body: " + message.body());

        deleteMessage(message);
    }

    private void deleteMessage(Message message) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

        sqsClient.deleteMessage(deleteRequest);
        System.out.println("✅ Mensagem deletada da fila: " + message.messageId());
    }
}
