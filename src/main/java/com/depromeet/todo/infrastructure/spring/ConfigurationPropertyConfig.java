package com.depromeet.todo.infrastructure.spring;

import com.depromeet.todo.infrastructure.kma.KmaWeatherConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        KmaWeatherConfig.class
})
public class ConfigurationPropertyConfig {
}
