package com.example.web;

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

@RestControllerAdvice
public class Base64DecodeBodyAdvice extends RequestBodyAdviceAdapter {

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
            var decodedBody = Base64.getDecoder().decode(getMessageData(inputStream));
            return new DecodedPubSubHttpInputMessage(inputMessage.getHeaders(), new ByteArrayInputStream(decodedBody));
        }
    }

    private String getMessageData(InputStream messageInputStream) throws IOException {
        var event = mapper.readValue(messageInputStream, PubSubEventDTO.class);
        if (Objects.isNull(event.message()) || Objects.isNull(event.message().data())) {
            return "";
        }
        return event.message().data();
    }


}
