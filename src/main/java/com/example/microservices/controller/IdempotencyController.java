package com.example.microservices.controller;

import com.example.microservices.component.Idempotency;
import com.example.microservices.component.api.RequestDto;
import com.example.microservices.component.api.ResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdempotencyController {

    private final Idempotency idempotency;

    public IdempotencyController(Idempotency idempotency) {
        this.idempotency = idempotency;
    }

    @PostMapping("/check_idempotency")
    public ResponseDto checkIdempotency(@RequestBody RequestDto requestDto) {
        return idempotency.checkIdempotency(requestDto);
    }
}
