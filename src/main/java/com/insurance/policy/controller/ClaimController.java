package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.ClaimInfoResponse;
import com.insurance.policy.dto.response.ClaimListResponseDto;
import com.insurance.policy.dto.response.ClaimResponseDto;
import com.insurance.policy.service.impl.web.ClaimServiceImpl;
import com.insurance.policy.service.impl.web.NotificationServiceImpl;
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
public class ClaimController extends BaseController {
    private final ClaimServiceImpl claimService;
    private final NotificationServiceImpl notificationService;

    @Operation(summary = "Retrieve all insurance claims for the specified user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_LIST)
    public ApiResponseDto<List<ClaimListResponseDto>> getList(
        @RequestHeader("userId") String userId,
        @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
        @Parameter(
                name = "language",
                description = "Locale for response localization. Accepts en_US or in_ID."
        ) final String language,
        @RequestParam(value = "channel", required = false, defaultValue = "web")
        @Parameter(
                name = "channel",
                description = "Source of request such as web or mobile.",
                example = "web"
        ) final String channel,
        @RequestParam(value = "requestId", required = false)
        @Parameter(
                name = "requestId",
                description = "Unique identifier per request. Auto-generated if missing.",
                example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
        ) String requestId
    ) {
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting ClaimController.getList()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            List<ClaimListResponseDto> response = claimService.getAllClaims(userId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute ClaimController.getList() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Retrieve claim details by claim ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_DETAIL)
    public ApiResponseDto<ClaimResponseDto> getDetail(
        @PathVariable Long claimId,
        @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
        @Parameter(
                name = "language",
                description = "Locale for response localization. Accepts en_US or in_ID."
        ) final String language,
        @RequestParam(value = "channel", required = false, defaultValue = "web")
        @Parameter(
                name = "channel",
                description = "Source of request such as web or mobile.",
                example = "web"
        ) final String channel,
        @RequestParam(value = "requestId", required = false)
        @Parameter(
                name = "requestId",
                description = "Unique identifier per request. Auto-generated if missing.",
                example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
        ) String requestId
    ) {
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting ClaimController.getDetail()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            ClaimResponseDto response = claimService.getClaimDetailsByClaimId(claimId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute ClaimController.getDetail() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Fetch all policy documents for a specific user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_POLICY_DOC)
    public ApiResponseDto<ClaimInfoResponse> getPolicyByUserId(
        @RequestHeader("userId") String userId,
        @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
        @Parameter(
                name = "language",
                description = "Locale for response localization. Accepts en_US or in_ID."
        ) final String language,
        @RequestParam(value = "channel", required = false, defaultValue = "web")
        @Parameter(
                name = "channel",
                description = "Source of request such as web or mobile.",
                example = "web"
        ) final String channel,
        @RequestParam(value = "requestId", required = false)
        @Parameter(
                name = "requestId",
                description = "Unique identifier per request. Auto-generated if missing.",
                example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
        ) String requestId
    ) {
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting ClaimController.getPolicyByUserId()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            ClaimInfoResponse response = claimService.getClaimInfoByUserId(userId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute ClaimController.getPolicyByUserId() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Submit a new insurance claim with attached documents.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.CLAIM_SUBMIT)
    public ApiResponseDto<ClaimResponseDto> submit(
        @RequestHeader(value = "userId", required = true) String userId,
        @RequestParam(value = "policyID", required = true) String policyId,
        @RequestParam(value = "claimTypeID", required = true)  String claimTypeId,
        @RequestPart(value = "files", required = true) List<MultipartFile> files,
        @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
        @Parameter(
                name = "language",
                description = "Locale for response localization. Accepts en_US or in_ID."
        ) final String language,
        @RequestParam(value = "channel", required = false, defaultValue = "web")
        @Parameter(
                name = "channel",
                description = "Source of request such as web or mobile.",
                example = "web"
        ) final String channel,
        @RequestParam(value = "requestId", required = false)
        @Parameter(
                name = "requestId",
                description = "Unique identifier per request. Auto-generated if missing.",
                example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
        ) String requestId
    ) {
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting ClaimController.submit()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            ClaimResponseDto response = claimService.submitClaim(policyId, userId, claimTypeId, files);

//            notificationService.notifyUser(
//                    userId,
//                    "You have successfully submitted your claim.",
//                    "SUBMIT_CLAIM",
//                    "/beneficiary/",
//                    "INFO",
//                    Long.valueOf(policyId)
//            );

            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute ClaimController.submit() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

//    @Operation(summary = "Download a claim file by its key name")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
//        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
//        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
//    })
//    @PostMapping(path = ApiConstant.INSURANCE.CLAIM_DOWNLOAD)
//    public ApiResponseDto<?> downloadFile(
//        @Valid @RequestBody
//        @Parameter(
//                name = "request",
//                description = "Payload containing download file request.",
//                required = true
//        ) final  Map<String, String> request,
//        @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
//        @Parameter(
//                name = "language",
//                description = "Locale for response localization. Accepts en_US or in_ID."
//        ) final String language,
//        @RequestParam(value = "channel", required = false, defaultValue = "web")
//        @Parameter(
//                name = "channel",
//                description = "Source of request such as web or mobile.",
//                example = "web"
//        ) final String channel,
//        @RequestParam(value = "requestId", required = false)
//        @Parameter(
//                name = "requestId",
//                description = "Unique identifier per request. Auto-generated if missing.",
//                example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
//        ) String requestId
//    ) {
//        requestId = this.resolveRequestId(requestId);
//        log.info("[RequestId: {}] Starting ClaimController.downloadFile()", requestId);
//
//        String keyName = request.get("keyName");
//        HttpStatus httpStatus = HttpStatus.OK;
//
//        try (S3ObjectInputStream s3InputStream = awsS3Service.downloadFile(keyName)) {
//            byte[] fileContent = IOUtils.toByteArray(s3InputStream);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//            String filename = keyName.substring(keyName.lastIndexOf(GeneralConstant.SLASH) + 1);
//            headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
//
//            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), fileContent, MessageConstants.HttpDescription.OK_DESC);
//        } catch (IOException e) {
//            log.info("[RequestId: {}] Execute ClaimController.downloadFile() ERROR: {}",
//                    requestId, e.getMessage());
//            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, e.getMessage());
//        } catch (Exception e) {
//            log.info("[RequestId: {}] Execute ClaimController.downloadFile() ERROR {}",
//                    requestId, e.getMessage());
//            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
//        }
//    }
}