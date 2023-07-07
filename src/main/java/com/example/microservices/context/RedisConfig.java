package com.example.microservices.context;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.ReplicatedServersConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient(@Value("${redis.server.address}") String redisServerAddress) {
        Config config = new Config();
        ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
        replicatedServersConfig.setReadMode(ReadMode.MASTER);
        replicatedServersConfig.addNodeAddress(redisServerAddress);
        return Redisson.create(config);
    }
}
