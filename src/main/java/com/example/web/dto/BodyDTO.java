package com.example.web.dto;

import java.util.Objects;
import java.util.UUID;

public record BodyDTO(UUID paymentId, String status) {
    public BodyDTO {
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
        Objects.requireNonNull(status, "status cannot be null");
    }
}
