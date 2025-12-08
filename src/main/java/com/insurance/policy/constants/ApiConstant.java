package com.insurance.policy.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstant {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class INSURANCE {
        public static final String QUOTATION_PLAN = "plan/get-quotation-plan";
        public static final String GET_TERMS = "terms";
        public static final String GET_POLICIES = "policy/getAll";
        public static final String GET_POLICY_WITH_ID = "policy/{id}";
        public static final String GET_POLICY_WITH_ID_AND_QUOTATION = "policy/quotation/{id}";
        public static final String CREATE_APPLICATION = "policy/create-application";
        public static final String CREATE_BENEFICIARIES = "policy/beneficiary";
        public static final String GET_NOTIFICATIONS = "notifications/getAll";

        public static final String BASE_QUOTATION = "quotation";
        public static final String QUOTATION_LIST = BASE_QUOTATION + "/list";
        public static final String QUOTATION_LIST_WITH_STATUS = BASE_QUOTATION + "/list/status/{status}";
        public static final String QUOTATION_LIST_WITH_USER_ID = BASE_QUOTATION + "/list/user/{userId}";
        public static final String QUOTATION_LIST_WITH_ID = BASE_QUOTATION + "/{id}";

        public static final String BASE_BENEFICIARY = "beneficiary";
        public static final String BENEFICIARY_LIST = BASE_BENEFICIARY + "/list";
        public static final String BENEFICIARY_LIST_WITH_POLICY_NO = BASE_BENEFICIARY + "/list/{policyNo}";
        public static final String BENEFICIARY_LIST_WITH_POLICY_ID = BASE_BENEFICIARY + "/list/policy/{policyId}";
        public static final String BENEFICIARY_LIST_CREATE = BASE_BENEFICIARY + "/list/create";

        public static final String BASE_AUTH = "auth";
        public static final String SIGNUP = BASE_AUTH + "/signup";
        public static final String LOGIN = BASE_AUTH + "/login";
        public static final String LOGIN_AUTH0 = BASE_AUTH + "/loginAuth0";

        public static final String BASE_PAYMENT = "payment";
        public static final String PAYMENT_LIST = BASE_PAYMENT + "/list";
        public static final String PAYMENT_LIST_WITH_STATUS = BASE_PAYMENT + "/list/status/{status}";
        public static final String PAYMENT_PROCESS = BASE_PAYMENT + "/process";

        public static final String BASE_CLAIM = "claim";
        public static final String CLAIM_LIST = BASE_CLAIM + "/list";
        public static final String CLAIM_DETAIL = BASE_CLAIM + "/detail/{claimId}";
        public static final String CLAIM_POLICY_DOC = BASE_CLAIM + "/claimpolicydocument";
        public static final String CLAIM_SUBMIT = BASE_CLAIM + "/submit";
        public static final String CLAIM_DOWNLOAD = BASE_CLAIM + "/download";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AUTH0 {
        public static final String AUTHORIZE_URL = "authorize";
        public static final String DEVICE_AUTHORIZATION_URL = "oauth/device/code";
        public static final String TOKEN_URL = "oauth/token";
        public static final String USER_INFO_URL = "userinfo";
        public static final String OPENID_CONFIG = ".well-known/openid-configuration";
        public static final String JWKS_JSON = ".well-known/jwks.json";
    }
}