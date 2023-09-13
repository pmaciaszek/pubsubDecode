package com.example.web.config;

import com.example.web.dto.PubSubEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
public class Base64DecodeBodyAdvice extends RequestBodyAdviceAdapter {

    public static final String EMPTY_JSON = "{}";

    private final ObjectMapper mapper;

    public Base64DecodeBodyAdvice(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(PubSubMessageBody.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        try (InputStream inputStream = inputMessage.getBody()) {
            return new DecodedPubSubHttpInputMessage(inputMessage.getHeaders(), new ByteArrayInputStream(decodeBody(inputStream)));
        }
    }

    private byte[] decodeBody(InputStream inputStream) throws IOException {
        return Optional.ofNullable(getMessageData(inputStream))
                .map(Base64.getDecoder()::decode)
                .orElse(EMPTY_JSON.getBytes());

    }

    private String getMessageData(InputStream messageInputStream) throws IOException {
        var event = mapper.readValue(messageInputStream, PubSubEventDTO.class);
        if (Objects.isNull(event.message()) || Objects.isNull(event.message().data())) {
            return null;
        }
        return event.message().data();
    }


}
