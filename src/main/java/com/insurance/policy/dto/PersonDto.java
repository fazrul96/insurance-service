package com.insurance.policy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonDto {
    @NotBlank(message = "Gender is required.")
    public String gender;

    public Integer age;

    public String title;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Nationality is required.")
    public String nationality;

    @NotBlank(message = "Identification number is required.")
    public String identificationNo;

    public String otherId;

    @NotNull
    public Boolean isUsPerson;

    @Email(message = "Invalid email format.")
    public String email;

    @NotNull(message = "Date of birth is required.")
    public Date dateOfBirth;

    @NotNull
    public boolean isSmoker;

    public String countryOfBirth;
    public Integer cigarettesNo;
    public String countryCode;
    public String phoneNo;
    public String occupation;
    public String purposeOfTransaction;
}
