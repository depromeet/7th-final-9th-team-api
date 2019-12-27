package org.springframework.web.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UriComponentBuilderTest {
    @Test
    void fromHttpUrlTest() {
        String requestUrl = UriComponentsBuilder.fromHttpUrl("http://naver.com")
                .path("/api/posts")
                .build(true)
                .toString();
        assertThat(requestUrl).isEqualTo("http://naver.com/api/posts");
    }
}
