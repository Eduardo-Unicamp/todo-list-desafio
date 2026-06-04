package com.saldanha.todo_list.service;


import com.saldanha.todo_list.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.Map;
import java.util.UUID;


@Service
public class FileStorageService {
    private final WebClient webClient;
    private final String bucketName,bucketUrl;


    public FileStorageService(
            @Value("${supabase.url}") String bucketUrl,
            @Value("${supabase.key}")String bucketSecretKey,
            @Value("${supabase.bucket}") String bucketName, FileRepository fileRepository
    ){
        this.bucketName=bucketName;
        this.bucketUrl=bucketUrl;
        this.webClient = WebClient.builder()
                .baseUrl(bucketUrl+"/storage/v1/object")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bucketSecretKey)
                .build();
    }

    public String uploadFile(MultipartFile file, UUID taskId) throws IOException {
        String fileName = UUID.randomUUID()+"-"+file.getOriginalFilename();
        String filePath = taskId.toString()+"/"+fileName;

        webClient.post()
                .uri("/{bucketName}/{filePath}",bucketName,filePath)
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .bodyValue(file.getBytes())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return filePath;
    }
    public String generateSignedUrl(String filePath){
        Map<String,Integer> body = Map.of("expiresIn",3600);

        @SuppressWarnings("unchecked")
        Map<String,String> response = webClient.post()
                .uri("/sign/{bucketName}/{filePath}",bucketName,filePath)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return bucketUrl +response.get("signedURL");
    }

}
