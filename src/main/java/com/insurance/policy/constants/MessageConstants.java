package com.insurance.policy.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageConstants {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ResponseMessages {
        public static final String SUCCESS = "Success";
        public static final String ERROR = "Error";
        public static final String FAILURE = "Failure";
        public static final String GET_SUCCESS = "Get successfully!";
        public static final String CREATE_SUCCESS = "Created successfully!";
        public static final String UPDATE_SUCCESS = "Updated successfully!";
        public static final String DELETE_SUCCESS = "Deleted successfully!";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Parameter {
        public static final String REQUEST = "request";
        public static final String REQUEST_ID = "requestId";
        public static final String LANGUAGE = "language";
        public static final String CHANNEL = "channel";
    }
}
