package com.example.web.controler;

import com.example.web.config.PubSubMessageBody;
import com.example.web.dto.BodyDTO;
import com.example.web.dto.PubSubMessageDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("base64")
    public void test(@RequestBody @PubSubMessageBody BodyDTO body) {
        System.out.println("Message with body: " + body);
    }

    @PostMapping
    public String hello(@RequestBody String name) {
        return "Hello " + name;
    }

    @PostMapping("meh")
    public String meh(@RequestBody PubSubMessageDTO messageDTO) {
        return messageDTO.toString();
    }
}
