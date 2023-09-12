package com.example.web;

import java.util.UUID;

public record BodyDTO(UUID paymentId, String status) {
}
