package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PubSubEventDTO(String subscription, PubSubMessageDTO message) {
}
