package com.insurance.policy.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.insurance.policy.constants.ModelConstant.CREATED_AT;
import static com.insurance.policy.constants.ModelConstant.UPDATED_AT;

@Data
@MappedSuperclass
public class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = CREATED_AT, updatable = false)
    @JsonProperty("createdAt")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = UPDATED_AT)
    @JsonProperty("updatedAt")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}