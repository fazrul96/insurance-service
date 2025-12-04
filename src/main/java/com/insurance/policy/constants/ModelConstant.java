package com.insurance.policy.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelConstant {
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String CREATED_DATE = "created_date";
    public static final String UPDATED_DATE = "updated_date";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Tables {
        public static final String USERS = "users";
        public static final String PLAN = "plan";
        public static final String RULE_SET = "ruleset";
        public static final String NOTIFICATION = "notification";
        public static final String CLAIM = "claim";
        public static final String CLAIM_DOCUMENT = "claim_document";
        public static final String CLAIM_TYPE = "claim_type";
        public static final String BENEFICIARY = "beneficiary";
        public static final String DOCUMENT_TYPE = "document_type";
        public static final String PAYMENT = "payment";
        public static final String POLICY = "policy";
        public static final String QUOTATION_APPLICATION = "quotation_application";
        public static final String TERMS_DECLARATION = "terms_declaration";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Columns {
        public static final String ID = "id";
        public static final String PLAN_ID = "plan_id";
        public static final String PLAN_NAME = "plan_name";
        public static final String COVERAGE_AMOUNT = "coverage_amount";
        public static final String BASE_PREMIUM = "base_premium";
        public static final String DURATION = "duration";
        public static final String STATUS = "status";

        public static final String BENEFICIARY_ID = "beneficiary_id";
        public static final String POLICY_ID = "policy_id";
        public static final String BENEFICIARY_NAME = "beneficiary_name";
        public static final String RELATIONSHIP_TO_INSURED = "relationship_to_insured";
        public static final String SHARE = "share";

        public static final String CLAIM_ID = "claim_id";
        public static final String USER_ID = "user_id";
        public static final String CLAIM_TYPE_ID = "claim_type_id";
        public static final String CLAIM_DATE = "claim_date";
        public static final String CLAIM_STATUS = "claim_status";

        public static final String CLAIM_DOCUMENT_ID = "claim_document_id";
        public static final String DOCUMENT_TYPE_ID = "document_type_id";
        public static final String CLAIM_DOCUMENT_URL = "claim_document_url";
        public static final String CLAIM_DOCUMENT_UPLOAD = "claim_document_upload";

        public static final String CLAIM_TYPE_NAME = "claim_type_name";
        public static final String CLAIM_TYPE_DESCRIPTION = "claim_type_description";

        public static final String DOCUMENT_TYPE_NAME = "document_type_name";
        public static final String DOCUMENT_TYPE_IS_REQUIRED = "document_type_is_required";

        public static final String PAYMENT_ID = "payment_id";
        public static final String PAYMENT_DATE = "payment_date";
        public static final String PAYMENT_AMOUNT = "payment_amount";
        public static final String PAYMENT_STATUS = "payment_status";
        public static final String QUOTATION_APPLICATION_ID = "quotation_application_id";

        public static final String QUOTATION_ID = "quotation_id";

        public static final String POLICY_NO = "policy_no";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";

        public static final String FULL_NAME = "full_name";
        public static final String GENDER = "gender";
        public static final String NATIONALITY = "nationality";
        public static final String IDENTIFICATION_NO = "identification_no";
        public static final String OTHER_ID = "other_id";
        public static final String COUNTRY_OF_BIRTH = "country_of_birth";
        public static final String COUNTRY_CODE = "country_code";
        public static final String PHONE_NO = "phone_no";
        public static final String EMAIL = "email";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String AGE = "age";
        public static final String TITLE = "title";
        public static final String IS_SMOKER = "is_smoker";
        public static final String IS_US_PERSON = "is_us_person";
        public static final String CIGARETTES_NO = "cigarettes_no";
        public static final String OCCUPATION = "occupation";
        public static final String PURPOSE_OF_TRANSACTION = "purpose_of_transaction";
        public static final String APPLICATION_STATUS = "application_status";
        public static final String REFERENCE_NUMBER = "reference_number";
        public static final String PREMIUM_AMOUNT = "premium_amount";
        public static final String PREMIUM_MODE = "premium_mode";
        public static final String QUOTATIONAPPLICATION = "quotationApplication";

        public static final String RULESET_ID = "ruleset_id";
        public static final String RULE_TYPE = "rule_type";
        public static final String OPERATOR = "operator";
        public static final String AGE_LIMIT = "age_limit";
        public static final String PAYMENT_FREQUENCY = "payment_frequency";

        public static final String TERMS_ID = "terms_id";
        public static final String TERMS_HTML = "terms_html";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
        public static final String IS_REQUIRED = "is_required";
    }
}