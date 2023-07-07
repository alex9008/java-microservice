package com.example.microservices.component;

import com.example.microservices.component.api.RequestDto;
import com.example.microservices.component.api.ResponseDto;
import com.example.microservices.component.lock.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class IdempotencyService implements Idempotency {

    private final LockService lockService;

    public IdempotencyService(LockService lockService) {
        this.lockService = lockService;
    }

    public ResponseDto checkIdempotency(RequestDto requestDto) {

        log.info("Check idempotency for operationId {}", requestDto.getKey());

        return lockService.executeWithLock(requestDto.getKey(), () -> {

            if (isIdempotent(requestDto)) {
                // Simulate long running operation
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Operation is idempotent");
                return ResponseDto.builder()
                        .status("OK")
                        .message("Operation is idempotent")
                        .response("Return response stored in cache")
                        .isIdempotent(true)
                        .build();
            }
            log.info("Operation is not idempotent");
            return ResponseDto.builder()
                    .status("OK")
                    .message("Operation is not idempotent")
                    .response("Return response from operation")
                    .isIdempotent(false)
                    .build();
        });
    }

    private boolean isIdempotent(RequestDto requestDto) {
        return "123".equals(requestDto.getKey());
    }
}
