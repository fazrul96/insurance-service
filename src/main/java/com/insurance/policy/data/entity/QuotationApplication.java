package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.QUOTATION_APPLICATION)
public class QuotationApplication extends BaseModel {
    @Id
    @Column(name = Columns.QUOTATION_ID)
    @JsonProperty(Columns.QUOTATION_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Columns.FULL_NAME)
    @JsonProperty(Columns.FULL_NAME)
    private String fullName;

    @Column(name = Columns.GENDER)
    @JsonProperty(Columns.GENDER)
    private String gender;

    @Column(name = Columns.NATIONALITY)
    @JsonProperty(Columns.NATIONALITY)
    private String nationality;

    @Column(name = Columns.IDENTIFICATION_NO)
    @JsonProperty(Columns.IDENTIFICATION_NO)
    private String identificationNo;

    @Column(name = Columns.OTHER_ID)
    @JsonProperty(Columns.OTHER_ID)
    private String otherId;

    @Column(name = Columns.COUNTRY_OF_BIRTH)
    @JsonProperty(Columns.COUNTRY_OF_BIRTH)
    private String countryOfBirth;

    @Column(name = Columns.COUNTRY_CODE)
    @JsonProperty(Columns.COUNTRY_CODE)
    private String countryCode;

    @Column(name = Columns.PHONE_NO)
    @JsonProperty(Columns.PHONE_NO)
    private String phoneNo;

    @Column(name = Columns.EMAIL)
    @JsonProperty(Columns.EMAIL)
    private String email;

    @Column(name = Columns.DATE_OF_BIRTH)
    @JsonProperty(Columns.DATE_OF_BIRTH)
    private Date dateOfBirth;

    @Column(name = Columns.AGE)
    @JsonProperty(Columns.AGE)
    private Integer age;

    @Column(name = Columns.TITLE)
    @JsonProperty(Columns.TITLE)
    private String title;

    @Column(name = Columns.IS_SMOKER)
    @JsonProperty(Columns.IS_SMOKER)
    private boolean isSmoker;

    @Column(name = Columns.IS_US_PERSON)
    @JsonProperty(Columns.IS_US_PERSON)
    private boolean isUsPerson;

    @Column(name = Columns.CIGARETTES_NO)
    @JsonProperty(Columns.CIGARETTES_NO)
    private Integer cigarettesNo;

    @Column(name = Columns.OCCUPATION)
    @JsonProperty(Columns.OCCUPATION)
    private String occupation;

    @Column(name = Columns.PURPOSE_OF_TRANSACTION)
    @JsonProperty(Columns.PURPOSE_OF_TRANSACTION)
    private String purposeOfTransaction;

    @Column(name = Columns.APPLICATION_STATUS)
    @JsonProperty(Columns.APPLICATION_STATUS)
    private String applicationStatus; // PENDING, SUCCESS, FAILED

    @Column(name = Columns.REFERENCE_NUMBER)
    @JsonProperty(Columns.REFERENCE_NUMBER)
    private String referenceNumber;

    @Column(name = Columns.PREMIUM_AMOUNT)
    @JsonProperty(Columns.PREMIUM_AMOUNT)
    private BigDecimal premiumAmount;

    @Column(name = Columns.PREMIUM_MODE)
    @JsonProperty(Columns.PREMIUM_MODE)
    private String premiumMode;

    @ManyToOne
    @JoinColumn(name = Columns.PLAN_ID)
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = Columns.USER_ID)
    private User user;

    @OneToOne(mappedBy = Columns.QUOTATIONAPPLICATION)
    @JsonIgnore
    private Payment payment;

    @OneToOne(mappedBy = Columns.QUOTATIONAPPLICATION)
    @JsonBackReference //do not serialize (used to break loop)
    private Policy policy;
}