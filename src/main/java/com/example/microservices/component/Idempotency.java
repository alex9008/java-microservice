package com.example.microservices.component;

import com.example.microservices.component.api.RequestDto;
import com.example.microservices.component.api.ResponseDto;

public interface Idempotency {

    ResponseDto checkIdempotency(RequestDto requestDto);
}
