package com.insurance.policy.service.impl.web;

import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.UploadListResponseDto;
import com.insurance.policy.dto.response.UploadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageClientServiceImpl {
    private final WebClient webClient;

    @Value("${storage-service.base-url}")
    private String storageServiceBaseUrl;

    public List<String> uploadFiles(List<MultipartFile> files, String prefix) {
        MultipartBodyBuilder builder = buildMultipartBody(files, prefix);

        return webClient.post()
                .uri("/api/v1/uploadFiles")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDto<List<String>>>() {})
                .blockOptional()
                .map(ApiResponseDto::getData)
                .orElseThrow(() -> new RuntimeException("Upload failed"));
    }

    public UploadListResponseDto uploadFilesLatest(String requestId, String userId, List<MultipartFile> files, String prefix) {
        log.info("[RequestId: {}] Starting StorageClientServiceImpl.uploadFiles()", requestId);

        MultipartBodyBuilder builder = buildMultipartBody(files, prefix);

        ApiResponseDto<UploadListResponseDto> response = postToUploadEndpoint(userId, builder);
        return new  UploadListResponseDto(extractFilesFromResponse(response));
    }

    private ApiResponseDto<UploadListResponseDto> postToUploadEndpoint(String userId, MultipartBodyBuilder builder) {
        return webClient.post()
                .uri(storageServiceBaseUrl + "/api/v1/s3/uploadFiles")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("userId", userId)
//                .header("language", language)
//                .header("requestId", requestId)
//                .header("channel", channel)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponseDto<UploadListResponseDto>>() {})
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Upload failed"));
    }

    private MultipartBodyBuilder buildMultipartBody(List<MultipartFile> files, String prefix) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        for (MultipartFile file : files) {
            builder.part("files", file.getResource())
                    .filename(Objects.requireNonNull(file.getOriginalFilename()))
                    .contentType(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())));
        }

        if (prefix != null) {
            builder.part("prefix", prefix);
        }

        return builder;
    }

    private List<UploadResponseDto> extractFilesFromResponse(ApiResponseDto<UploadListResponseDto> response) {
        UploadListResponseDto data = response.getData();
        if (data == null || data.getUploadList() == null) {
            return Collections.emptyList();
        }
        return data.getUploadList();
    }
}