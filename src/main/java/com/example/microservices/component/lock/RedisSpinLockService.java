package com.example.microservices.component.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


@Component
@Slf4j
@Primary
public class RedisSpinLockService implements LockService {

    private final RedissonClient redissonClient;

    @Value("${lock.lease-time-seconds:10}")
    private Long leaseTime;

    public RedisSpinLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <T> T executeWithLock(String idemKey, Supplier<T> supplier) {

        if (StringUtils.isBlank(idemKey)) {
            return supplier.get();
        }

        log.debug("Trx lockId {}", idemKey);
        RLock lock = redissonClient.getSpinLock(idemKey);
        try {
            lock.lock(leaseTime, TimeUnit.SECONDS);
            log.info("Trx lockId acquired {}", idemKey);
            return supplier.get();
        } finally {
            unlock(lock);
        }
    }

    private void unlock(RLock rLock) {
        try {
            if (rLock.isLocked()) {
                log.debug("Trx unlock lockId {} ", rLock.getName());
                rLock.unlock();
            }
        } catch (IllegalMonitorStateException ex){
            log.error("Trx unlock lockId {} ", rLock.getName(), ex);
        }
    }
}
