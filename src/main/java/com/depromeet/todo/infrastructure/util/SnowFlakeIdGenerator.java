package com.depromeet.todo.infrastructure.util;

import com.depromeet.todo.domain.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SnowFlakeIdGenerator implements IdGenerator {
    private static final Random RANDOM = new Random();
    // starts at  2019.12.25.
    private static final long START_EPOCH_MILLIS = 1577232000000L;
    // left shift amounts
    private static final int TIMESTAMP_SHIFT = 23;
    // exclusive
    private static final int MAX_RANDOM = 0x800000;

    @Override
    public Long generate() {
        long time = System.currentTimeMillis() - START_EPOCH_MILLIS;
        return (time << TIMESTAMP_SHIFT) + RANDOM.nextInt(MAX_RANDOM);
    }
}
