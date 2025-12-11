package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.ClaimInfoResponse;
import com.insurance.policy.dto.response.ClaimListResponseDto;
import com.insurance.policy.dto.response.ClaimResponseDto;
import com.insurance.policy.service.impl.web.ClaimServiceImpl;
import com.insurance.policy.util.common.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class ClaimController extends BaseController {
    private final ClaimServiceImpl claimService;

    @Override
    protected String getControllerName() {
        return "ClaimController";
    }

    @Operation(summary = "Retrieve all insurance claims for the specified user.")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_LIST)
    public ApiResponseDto<List<ClaimListResponseDto>> getAllClaims(RequestContext context) {
        logRequest(context.getRequestId(), "ClaimController.getList()");
        return handleRequest(context, () -> claimService.getAllClaims(context.getUserId()));
    }

    @Operation(summary = "Retrieve claim details by claim ID.")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_DETAIL)
    public ApiResponseDto<ClaimResponseDto> getClaimDetail(RequestContext context, @PathVariable Long claimId) {
        logRequest(context.getRequestId(), "ClaimController.getClaimDetail()");
        return handleRequest(context, () -> claimService.getClaimDetailsByClaimId(claimId));
    }

    @Operation(summary = "Fetch all policy documents for a specific user.")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_POLICY_DOC)
    public ApiResponseDto<ClaimInfoResponse> getPolicyDocuments(RequestContext context) {
        logRequest(context.getRequestId(), "ClaimController.getPolicyDocuments()");
        return handleRequest(context, () -> claimService.getClaimInfoByUserId(
                context.getRequestId(), context.getUserId())
        );
    }

    @Operation(summary = "Submit a new insurance claim with attached documents.")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.CLAIM_SUBMIT)
    public ApiResponseDto<ClaimResponseDto> submitClaim(
            RequestContext context,
            @RequestParam("policyId") String policyId,
            @RequestParam("claimTypeId")  String claimTypeId,
            @RequestPart("files") List<MultipartFile> files
    ) {
        logRequest(context.getRequestId(), "ClaimController.submitClaim()");
        return handleRequest(context, () -> claimService.submitClaim(
                context.getRequestId(), context.getUserId(), Long.valueOf(policyId),
                Long.valueOf(claimTypeId), files, context.getPrefix())
        );
    }

    @Operation(summary = "Download a claim file by its key name")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_DOWNLOAD)
    public ResponseEntity<Resource> downloadClaimFile(
            RequestContext context, @RequestParam("documentKey") String documentKey
    ) {
        logRequest(context.getRequestId(), "ClaimController.downloadClaimFile()");

        Resource response = claimService.downloadByDocumentKey(
                context.getRequestId(), context.getUserId(), documentKey
        );
        String filename = StringUtils.extractFileNameFromPath(documentKey);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }
}