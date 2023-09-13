package com.example.web.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;

public class DecodedPubSubHttpInputMessage implements HttpInputMessage {

    private final HttpHeaders headers;
    private final InputStream body;

    public DecodedPubSubHttpInputMessage(HttpHeaders headers, InputStream body) {
        super();
        this.headers = headers;
        this.body = body;
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
