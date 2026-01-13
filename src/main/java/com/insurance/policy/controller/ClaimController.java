package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.ClaimInfoResponse;
import com.insurance.policy.dto.response.ClaimListResponseDto;
import com.insurance.policy.dto.response.ClaimResponseDto;
import com.insurance.policy.service.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class ClaimController extends BaseController {
    private final ClaimService claimService;

    @Override
    protected String getControllerName() {
        return "ClaimController";
    }

    @Operation(summary = "Retrieve all insurance claims for the specified user.")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_LIST)
    public ApiResponseDto<List<ClaimListResponseDto>> getAllClaims(RequestContext context) {
        return handleRequest(getControllerName() + "getList",
                context, () -> claimService.getAllClaims(context.getUserId())
        );
    }

    @Operation(summary = "Retrieve claim details by claim ID.")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_DETAIL)
    public ApiResponseDto<ClaimResponseDto> getClaimDetail(RequestContext context, @PathVariable Long claimId) {
        return handleRequest(getControllerName() + "getClaimDetail",
                context, () -> claimService.getClaimDetailsByClaimId(claimId)
        );
    }

    @Operation(summary = "Fetch all policy documents for a specific user.")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_POLICY_DOC)
    public ApiResponseDto<ClaimInfoResponse> getPolicyDocuments(RequestContext context) {
        return handleRequest(getControllerName() + "getPolicyDocuments",
                context, () -> claimService.getClaimInfoByUserId(
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
        return handleRequest(getControllerName() + "submitClaim",
                context, () -> claimService.submitClaim(
                context.getRequestId(), context.getUserId(), Long.valueOf(policyId),
                Long.valueOf(claimTypeId), files, context.getPrefix())
        );
    }

    @Operation(summary = "Download a claim file by its key name")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.CLAIM_DOWNLOAD)
//    public void downloadClaimFile(RequestContext context, @RequestParam("documentKey") String documentKey,
//                                  HttpServletResponse response) {
//        claimService.downloadByDocumentKey(context.getRequestId(), context.getUserId(), documentKey, response);
//    }

    public void downloadClaim(@RequestParam String documentKey,
                              HttpServletResponse response) throws IOException {

        String queryParam = "?documentKey=" + documentKey;
        String redirectUrl = "http://localhost:8084/api/v1/s3/downloadFileByDocumentKey" +
                queryParam;

        response.sendRedirect(redirectUrl);
    }
}