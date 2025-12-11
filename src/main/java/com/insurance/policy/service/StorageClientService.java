package com.insurance.policy.service;

import com.insurance.policy.dto.response.UploadListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageClientService {

    UploadListResponseDto uploadFiles(String requestId, String userId, List<MultipartFile> files, String prefix);

    ResponseEntity<byte[]> processDownload(String requestId, String userId, String documentKey);
}
