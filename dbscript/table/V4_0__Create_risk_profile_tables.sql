CREATE TABLE `insurance_db`.`T_RISK_PROFILE` (
-- TODO refactor add risk profile columns
  `policy_id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` datetime(6) DEFAULT NULL,
  `policy_no` varchar(255) DEFAULT NULL,
  `premium_amount` decimal(38,2) DEFAULT NULL,
  `premium_mode` varchar(255) DEFAULT NULL,
  `reference_number` varchar(255) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payment_id` bigint DEFAULT NULL,
  `plan_id` bigint DEFAULT NULL,
  `quotation_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`policy_id`)
);