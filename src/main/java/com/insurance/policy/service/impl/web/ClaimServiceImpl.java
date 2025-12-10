package com.insurance.policy.service.impl.web;

import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.data.entity.*;
import com.insurance.policy.data.repository.*;
import com.insurance.policy.dto.response.*;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.ClaimService;
import com.insurance.policy.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.insurance.policy.constants.GeneralConstant.S3.DEFAULT_PREFIX;
import static com.insurance.policy.constants.GeneralConstant.STATUS.PENDING;
import static com.insurance.policy.dto.request.NotificationRequestDto.buildNotification;
import static com.insurance.policy.util.enums.NotificationTemplate.CLAIM_UPLOAD_SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings({
        "PMD.FormalParameterNamingConventions",
        "PMD.UseIndexOfChar",
        "PMD.ExcessiveParameterList",
        "PMD.NullAssignment",
        "PMD.AvoidCatchingNPE",
        "PMD.PreserveStackTrace",
        "PMD.UseUnderscoresInNumericLiterals",
        "PMD.NcssCount",
        "PMD.CognitiveComplexity",
        "PMD.CyclomaticComplexity",
        "PMD.NPathComplexity",
        "PMD.PrematureDeclaration",
        "PMD.LocalVariableNamingConventions",
        "PMD.UselessParentheses",
        "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.AvoidThrowingNullPointerException"})
public class ClaimServiceImpl implements ClaimService {
    private final ClaimTypeRepository claimTypeRepository;
    private final PolicyServiceImpl policyService;
    private final ClaimRepository claimRepository;
    private final ClaimTypeServiceImpl claimTypeService;
    private final ClaimDocumentRepository claimDocumentRepository;
    private final UserRepository userRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final UserServiceImpl userService;
    private final StorageClientServiceImpl storageClientService;
    private final NotificationService notificationService;

    public List<ClaimListResponseDto> getAllClaims(String userId) {
        return claimRepository.findClaimListByUserId(userId);
    }

    public ClaimResponseDto submitClaim(String requestId, String userId, Long policyId, Long claimTypeId,
            List<MultipartFile> files, String prefix
    ) throws WebException {
        log.info("[RequestId: {}] Execute ClaimServiceImpl.submitClaim()", requestId);

        validateSubmittedDocuments(files, claimTypeId);

        ClaimType claimtype = getClaimTypeById(claimTypeId);
        Policy policy = policyService.findPolicyById(requestId, policyId);
        User user = userService.getUserByUserId(requestId, userId);

        Claim claim = toClaim(user, claimtype, policy);
        createClaim(claim);

        String folderName = buildFolderName(prefix, userId, claim.getClaimId(), policyId);

        UploadListResponseDto uploadedFiles = storageClientService.uploadFiles(requestId, userId, files, folderName);

        List<Map<String, String>> documentList = saveClaimDocuments(claim, claimTypeId, uploadedFiles);

        notificationService.notifyUser(buildNotification(requestId, null, CLAIM_UPLOAD_SUCCESS));
        return toClaimResponse(claim, policy, documentList, claimtype);
    }

    public Resource downloadByDocumentKey (String requestId, String userId, String documentKey) throws WebException {
        ResponseEntity<byte[]> response = storageClientService.processDownload(requestId, userId, documentKey);

        byte[] data = response.getBody();
        if (data == null || response.getStatusCode() != HttpStatus.OK) {
            log.error("[RequestId: {}] Execute ClaimServiceImpl.downloadFile()", requestId);
            throw new WebException("Failed to download file from storage service");
        }

        return new ByteArrayResource(data);
    }

    public ClaimInfoResponse getClaimInfoByUserId(String userId) {
        try {
            // Check if user exists
            Optional<User> userOptional = userRepository.findByUserId(userId);
            if (userOptional.isPresent()) {

                log.info("User found: {}", userOptional.get().getUsername());

                //Get policy IDs
//                List<Policy> policy = policyRepository.findByUserId(String.valueOf(userId));
                List<Policy> policy = null;
                List<Map<String,String>> policyInfo = new ArrayList<>();
                for (Policy p : policy) {

                    Map<String, String> policyMap = new HashMap<>();
                    policyMap.put("policyId",String.valueOf(p.getId()) );
                    policyMap.put("policyNo", p.getPolicyNo());
                    policyInfo.add(policyMap);
                }
                log.info("List of policy info: {}", policyInfo);

                // Get claim type & required documents
                List<Object[]> rows = claimTypeRepository.getClaimTypesWithDocuments();

                Map<Long, ClaimInfoResponse.ClaimPolicyDocument> claimMap = new LinkedHashMap<>();

                for (Object[] row : rows) {
                    Long claimTypeId = (Long) row[0];
                    String claimType = (String) row[1];
                    String claimDescription = (String) row[2];
                    String documentName = (String) row[3];
                    claimMap.computeIfAbsent(claimTypeId, id -> {
                        ClaimInfoResponse.ClaimPolicyDocument doc = new ClaimInfoResponse.ClaimPolicyDocument();
                        doc.setClaimTypeId(id);
                        doc.setClaimTypeName(claimType);
                        doc.setClaimDescription(claimDescription);
                        doc.setRequiredDocuments(new ArrayList<>());
                        return doc;
                    }).getRequiredDocuments().add(documentName);
                }

                ClaimInfoResponse response = new ClaimInfoResponse();
                response.setPolicyInfo(policyInfo);
                response.setClaimPolicyDocument(new ArrayList<>(claimMap.values()));
                return response;
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            log.error("Error fetching claim info: {}", e.getMessage());
            throw new RuntimeException("Error fetching claim info: " + e.getMessage());
        }
    }

    public ClaimResponseDto getClaimDetailsByClaimId(Long claimId) throws WebException {
        Claim claim = getClaimById(claimId);
        ClaimType claimType = claimTypeService.getClaimTypeByClaimId(claimId);
        List<ClaimDocument> documentList = claimTypeService.getClaimDocumentByClaimId(claimId);

        List<Map<String, String>> documentListMap = new ArrayList<>();
        if (documentList != null) {
            for (ClaimDocument doc : documentList) {
                Map<String, String> documentMap = new HashMap<>();
                documentMap.put("documentName", doc.getDocumentType().getDocumentTypeName());
                documentMap.put("documentUrl", doc.getDocumentUrl());
                documentMap.put("documentKey", doc.getDocumentUrl());
                documentListMap.add(documentMap);
            }
        }

        return toClaimResponse(claimType, documentListMap, claim);
    }

    private Claim getClaimById(Long claimId) throws WebException {
        return claimRepository.findById(claimId)
                .orElseThrow(() -> new WebException("Claim not found"));
    }

    private List<Map<String, String>> saveClaimDocuments(Claim claim, Long claimTypeId, UploadListResponseDto uploadedFiles) {
        List<DocumentType> requiredDocs = documentTypeRepository.findByClaimTypeId(String.valueOf(claimTypeId));
        List<Map<String, String>> documentList = new ArrayList<>();

        for (DocumentType docType : requiredDocs) {
            for (UploadResponseDto file : uploadedFiles.getUploadList()) {
                if (file.getFilename().contains(docType.getDocumentTypeName())) {
                    ClaimDocument claimDocument = buildClaimDocument(claim, docType, file);
                    createClaimDocument(claimDocument);
                    documentList.add(mapToResponse(file));
                }
            }
        }
        return documentList;
    }

    private ClaimType getClaimTypeById(Long claimTypeId) {
        return claimTypeRepository.findById(claimTypeId).orElse(null);
    }

    private ClaimDocument buildClaimDocument(Claim claim, DocumentType docType, UploadResponseDto file) {
        return ClaimDocument.builder()
                .claim(claim)
                .documentType(docType)
                .documentUrl(file.getUrl())
                .documentUpload(LocalDate.now())
                .build();
    }

    private Map<String, String> mapToResponse(UploadResponseDto file) {
        Map<String, String> map = new HashMap<>();
        map.put("documentName", file.getFilename().replaceFirst("\\.[^.]+$", ""));
        map.put("documentUrl", file.getUrl());
        return map;
    }

    /**
     * Validates that uploaded documents meet the required documents
     * for a given claim type.
     *
     * A filename is interpreted as the document type BEFORE the extension.
     * Example:
     *   - "KTP.jpg"       → "KTP"
     *   - "Diagnosis.pdf" → "Diagnosis"
     *
     * @param uploadedDocuments list of files uploaded
     * @param claimTypeId       claim type ID used to determine required documents
     * @throws WebException if required documents are missing or validation fails
     */
    private void validateSubmittedDocuments(List<MultipartFile> uploadedDocuments, Long claimTypeId) throws WebException {
        validateDocumentsExist(uploadedDocuments);

        List<DocumentType> requiredDocumentTypes =
                documentTypeRepository.findByClaimTypeId(String.valueOf(claimTypeId));

        Set<String> submittedDocumentNames = extractDocumentNames(uploadedDocuments);

        for (DocumentType docType : requiredDocumentTypes) {
            if (docType.isRequired()) {
                validateRequiredDocument(submittedDocumentNames, docType.getDocumentTypeName());
            }
        }
    }

    /**
     * Validates that the uploaded document list is not null or empty.
     *
     * @param documents list of uploaded MultipartFile documents
     * @throws WebException if no documents were submitted
     */
    private void validateDocumentsExist(List<MultipartFile> documents) throws WebException {
        if (documents == null || documents.isEmpty()) {
            throw new WebException("No documents submitted");
        }
    }

    /**
     * Validates that a required document type is included in the submitted document names.
     *
     * @param submittedDocumentNames set of normalized document names uploaded by the user
     * @param requiredName           expected required document type name
     * @throws WebException if required document was not submitted
     */
    private void validateRequiredDocument(Set<String> submittedDocumentNames, String requiredName) throws WebException {
        if (!submittedDocumentNames.contains(requiredName)) {
            throw new WebException("Missing required document: " + requiredName);
        }
    }

    private void createClaim(Claim claim) {
        claimRepository.save(claim);
    }

    private Claim toClaim(User user, ClaimType claimType, Policy policy) {
        return Claim.builder()
                .user(user)
                .claimType(claimType)
                .policy(policy)
                .claimDate(LocalDate.now())
                .claimStatus(PENDING)
                .build();
    }

    private ClaimResponseDto toClaimResponse(ClaimType claimType, List<Map<String, String>> documentListMap, Claim claim) {
        return ClaimResponseDto.builder()
                .claimType(claimType)
                .documentList(documentListMap)
                .policyNo(claim.getPolicy().getPolicyNo())
                .claimDate(claim.getClaimDate())
                .claimStatus(claim.getClaimStatus())
                .build();
    }

    private ClaimResponseDto toClaimResponse(Claim claim, Policy policy, List<Map<String,String>> documentList,  ClaimType claimtype) {
        return ClaimResponseDto.builder()
                .claimId(claim.getClaimId())
                .policyNo(policy.getPolicyNo())
                .documentList(documentList)
                .claimType(claimtype)
                .claimDate(LocalDate.now())
                .claimStatus(claim.getClaimStatus())
                .build();
    }

    private void createClaimDocument(ClaimDocument claimDocument) {
        claimDocumentRepository.save(claimDocument);
    }

    /**
     * Extracts document names from filenames by removing extensions and trimming spaces.
     * Example:
     *   "Diagnosis Report.pdf" → "Diagnosis Report"
     *
     * @param documents list of uploaded files
     * @return Set of clean document names (case-sensitive)
     */
    private Set<String> extractDocumentNames(List<MultipartFile> documents) {
        return documents.stream()
                .map(MultipartFile::getOriginalFilename)
                .filter(Objects::nonNull)
                .map(this::stripExtension)
                .collect(Collectors.toSet());
    }

    /**
     * Removes the file extension from a filename.
     * Example:
     *   "file.pdf" → "file"
     *
     * @param filename original filename
     * @return filename without extension
     */
    private String stripExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex <= 0) {  // no extension or hidden file like ".gitignore"
            return filename;
        }
        return filename.substring(0, dotIndex);
    }

    private String resolvePrefix(String prefix) {
        return (prefix == null || prefix.isBlank())
                ? DEFAULT_PREFIX
                : prefix;
    }

    private String buildFolderName(String prefix, String userId, Long claimId, Long policyId) {
        return resolvePrefix(prefix)
                + GeneralConstant.SLASH + userId
                + GeneralConstant.UNDERSCORE + claimId
                + GeneralConstant.UNDERSCORE + policyId;
    }
}
