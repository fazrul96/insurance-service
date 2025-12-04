package com.insurance.policy.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.policy.data.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.insurance.policy.constants.ModelConstant.Columns;
import static com.insurance.policy.constants.ModelConstant.Tables;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.USERS)
public class User extends BaseModel {
    @Id
    @Column(name = Columns.ID)
    @JsonProperty(Columns.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role; // e.g., ROLE_USER

    @Column(nullable = false)
    private String idType; // Identification type, e.g., NRIC

    @Column(unique = true, nullable = false)
    private String idNo; // Identification number, e.g., "101010-12-3456"

    @Column(nullable = false)
    private String mobileNoPrefix; // Mobile number prefix, e.g., "60"

    @Column(nullable = false)
    private String mobileNo; // Mobile number, e.g., "123456789"
}