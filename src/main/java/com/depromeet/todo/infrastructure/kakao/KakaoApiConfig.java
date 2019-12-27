package com.depromeet.todo.infrastructure.kakao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KakaoApiConfig {

    @Bean
    public RestTemplate kakaoApiRestTemplate() {
        return new RestTemplate();
    }
}
