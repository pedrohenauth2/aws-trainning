package aws.training.AWS_TRAINING.s3.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import aws.training.AWS_TRAINING.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "S3")
@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Operation(summary = "Upload de arquivo")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) throws IOException {
        String key = s3Service.upload(file);
        return ResponseEntity.ok("Arquivo enviado com sucesso! Key: " + key);
    }

    @Operation(summary = "Download de arquivo")
    @GetMapping("/download/{key}")
    public ResponseEntity<byte[]> download(@PathVariable String key) {
        byte[] bytes = s3Service.download(key);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + key + "\"")
                .body(bytes);
    }

    @Operation(summary = "Listar arquivos do bucket")
    @GetMapping("/listar")
    public ResponseEntity<List<String>> listar() {
        return ResponseEntity.ok(s3Service.listar());
    }

    @Operation(summary = "Deletar arquivo")
    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deletar(@PathVariable String key) {
        s3Service.deletar(key);
        return ResponseEntity.noContent().build();
    }
}
