package com.depromeet.todo.infrastructure.util;

import com.depromeet.todo.domain.IdGenerator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class SnowFlakeIdGenerator implements IdGenerator {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final long START_EPOCH_MILLIS = LocalDate.of(2019, 12, 25)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli();
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
