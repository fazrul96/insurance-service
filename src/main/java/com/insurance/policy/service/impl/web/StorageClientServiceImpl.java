package com.insurance.policy.service.impl.web;

import com.insurance.policy.component.InsuranceInternalClient;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.UploadListResponseDto;
import com.insurance.policy.dto.response.UploadResponseDto;
import com.insurance.policy.properties.AppProperties;
import com.insurance.policy.properties.StorageServiceProperties;
import com.insurance.policy.service.StorageClientService;
import com.insurance.policy.util.common.LogUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.insurance.policy.constants.ApiConstant.S3.DOWNLOAD_FILE_BY_DOCUMENT_KEY;
import static com.insurance.policy.constants.ApiConstant.S3.UPLOAD_FILES;

@Service
@RequiredArgsConstructor
public class StorageClientServiceImpl implements StorageClientService {
    private final AppProperties appProperties;
    private final StorageServiceProperties storageServiceProperties;
    private final InsuranceInternalClient insuranceClient;
    private final LogUtils logUtils;

    private String privateApi;

    @PostConstruct
    public void init() {
        this.privateApi = storageServiceProperties.getBaseUrl() + appProperties.getPrivateApiPath();
    }

    @Override
    public String getServiceName() {
        return "StorageClientServiceImpl";
    }

    @Override
    public UploadListResponseDto uploadFiles(
            String requestId, String userId, List<MultipartFile> files, String prefix) {
        logUtils.logRequest(requestId, getServiceName() + "uploadFiles");

        MultipartBodyBuilder builder = buildMultipartBody(files, prefix);

        ApiResponseDto<UploadListResponseDto> response = callUploadApi(userId, builder);
        return new UploadListResponseDto(extractFilesFromResponse(response));
    }

    @Override
    public ResponseEntity<byte[]> processDownload(String requestId, String userId, String documentKey) {
        logUtils.logRequest(requestId, getServiceName() + "downloadFile");

        String queryParam = "?documentKey=" + documentKey;
        return insuranceClient.client()
                .get()
                .uri(this.privateApi + DOWNLOAD_FILE_BY_DOCUMENT_KEY + queryParam)
                .header("userId", userId)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .toEntity(byte[].class)
                .block();
    }

    private ApiResponseDto<UploadListResponseDto> callUploadApi(String userId, MultipartBodyBuilder builder) {
        return insuranceClient.client()
                .post()
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
        return data != null && data.getUploadList() != null
                ? data.getUploadList()
                : Collections.emptyList();
    }
}