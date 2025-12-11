package com.insurance.policy.service.impl.web;

import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.UploadListResponseDto;
import com.insurance.policy.dto.response.UploadResponseDto;
import com.insurance.policy.properties.AppProperties;
import com.insurance.policy.service.impl.StorageClientService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.insurance.policy.constants.ApiConstant.S3.DOWNLOAD_FILE_BY_DOCUMENT_KEY;
import static com.insurance.policy.constants.ApiConstant.S3.UPLOAD_FILES;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageClientServiceImpl implements StorageClientService {
    private final WebClient webClient;
    private final AppProperties appProperties;

    @Value("${storage-service.base-url}")
    private String storageServiceBaseUrl;

    private String privateApi;
    private String publicApi;

    @PostConstruct
    public void init() {
        this.publicApi = storageServiceBaseUrl + appProperties.getPublicApiPath();
        this.privateApi = storageServiceBaseUrl + appProperties.getPrivateApiPath();
    }

    @Override
    public UploadListResponseDto uploadFiles(
            String requestId, String userId, List<MultipartFile> files, String prefix) {
        log.info("[RequestId: {}] Starting StorageClientServiceImpl.uploadFiles()", requestId);

        MultipartBodyBuilder builder = buildMultipartBody(files, prefix);

        ApiResponseDto<UploadListResponseDto> response = processUpload(userId, builder);
        return new UploadListResponseDto(extractFilesFromResponse(response));
    }

    @Override
    public ResponseEntity<byte[]> processDownload(String requestId, String userId, String documentKey) {
        log.info("[RequestId: {}] Starting StorageClientServiceImpl.downloadFile()", requestId);
        String queryParam = "?documentKey=" + documentKey;
        return webClient.get()
                .uri(this.privateApi + DOWNLOAD_FILE_BY_DOCUMENT_KEY + queryParam)
                .header("userId", userId)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .toEntity(byte[].class)
                .block();
    }

    private ApiResponseDto<UploadListResponseDto> processUpload(String userId, MultipartBodyBuilder builder) {
        return webClient.post()
                .uri(this.privateApi + UPLOAD_FILES)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("userId", userId)
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
        builder.part("prefix", prefix);
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