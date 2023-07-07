package com.example.microservices.component.lock;

import java.util.function.Supplier;

public interface LockService {
    <T> T executeWithLock(String idemKey, Supplier<T> supplier);
}
