package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.UploadListResponseDto;
import com.insurance.policy.service.impl.web.StorageClientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class StorageController extends BaseController {
    private final StorageClientServiceImpl storageClientService;

    @Operation(
            summary = "Upload files to S3 storage",
            description = "Handles file upload to S3 bucket with optional prefix for key path."
    )
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.S3.UPLOAD_FILES)
    public ApiResponseDto<UploadListResponseDto> uploadFiles(
            RequestContext context,
            @RequestParam("files")
            @Parameter(name = "files", description = "List of files to upload.", required = true)
            List<MultipartFile> files
    ) {
        logRequest(context.getRequestId(), "StorageController.uploadFiles()");
        return handleRequest(context, () -> storageClientService.uploadFiles(
                context.getRequestId(), context.getUserId(), files, context.getPrefix())
        );
    }
}