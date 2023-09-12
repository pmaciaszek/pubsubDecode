package com.example.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
@JsonIgnoreProperties(ignoreUnknown = true)
public record PubSubMessageDTO(String data, String messageId, Instant publishTime) {
}
