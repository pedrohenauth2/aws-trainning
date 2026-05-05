package aws.training.AWS_TRAINING.lambda.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import aws.training.AWS_TRAINING.lambda.controller.request.LambdaRequest;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
public class LambdaService {

    private final LambdaClient lambdaClient;

    @Value("${aws.lambda.function-name}")
    private String functionName;

    public LambdaService(LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    public String invocar(LambdaRequest lambdaRequest) {
        String payload = String.format("{\"numero\": %d}", lambdaRequest.numero());

        InvokeRequest request = InvokeRequest.builder()
                .functionName(functionName)
                .payload(SdkBytes.fromString(payload, StandardCharsets.UTF_8))
                .build();

        InvokeResponse response = lambdaClient.invoke(request);
        return response.payload().asUtf8String();
    }
}
