package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.UploadListResponseDto;
import com.insurance.policy.service.impl.web.StorageClientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK,
                    description = MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST,
                    description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR,
                    description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.S3.UPLOAD_FILES)
    public ApiResponseDto<UploadListResponseDto> uploadFiles(
            @RequestHeader("userId") String userId,
            @RequestParam("files")
            @Parameter(name = "files", description = "List of files to upload.", required = true
            ) List<MultipartFile> files,
            @RequestParam(value = "prefix", required = false)
            @Parameter(name = "prefix", description = "Optional prefix added to the file key paths.",
                    example = "documents/"
            ) String prefix,
            @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
            @Parameter(name = "language", description = "Locale for response localization (e.g., en_US, in_ID)."
            ) String language,
            @RequestParam(value = "channel", required = false, defaultValue = "web")
            @Parameter(
                    name = "channel", description = "Source of request such as web or mobile.", example = "web"
            ) String channel,
            @RequestParam(value = "requestId", required = false)
            @Parameter(
                    name = "requestId",
                    description = "Unique identifier for the request. Auto-generated if missing.",
                    example = "c1f23ba4-9123-4bd8-a1ea-9a123456abcd"
            ) String requestId
    ) {
        requestId = resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting S3Controller.uploadFiles()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            UploadListResponseDto response = storageClientService.uploadFilesLatest(requestId, userId, files, prefix);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);

        } catch (Exception e) {
            log.error("[RequestId: {}] S3Controller.uploadFiles() ERROR {}", requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null, e.getMessage());
        }
    }
}