package com.depromeet.todo.infrastructure.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class JwtApplicationServiceTest {
    private JwtApplicationService tokenService;
    private JwtApplicationService anotherIssuerJwtService;
    private JwtApplicationService anotherSigningKeyJwtService;

    @BeforeEach
    void setUp() {
        tokenService = new JwtApplicationService(
                "TOKEN_ISSUER",
                "TOKEN_SIGNING_KEY"
        );
        anotherIssuerJwtService = new JwtApplicationService(
                "ANOTHER_TOKEN_ISSUER",
                "TOKEN_SIGNING_KEY"
        );
        anotherSigningKeyJwtService = new JwtApplicationService(
                "TOKEN_ISSUER",
                "ANOTHER_TOKEN_SINGING_KEY"
        );
    }

    @Test
    void 생성_및_검증() {
        // given
        Long memberId = 1L;
        // when 1
        String token = tokenService.generate(memberId);
        // then 1
        assertThat(token).isNotBlank();

        // when 2
        Optional<Long> decodeResult = tokenService.decode(token);
        // then 2
        assertThat(decodeResult).hasValue(memberId);
    }

    @Test
    void token_issuer_는_값이_달라도_decode_성공해야함() {
        // given
        Long memberId = 1L;
        String tokenByAnotherSigningKey = anotherIssuerJwtService.generate(memberId);
        assertThat(tokenByAnotherSigningKey).isNotBlank();
        // when
        Optional<Long> decodeResult = tokenService.decode(tokenByAnotherSigningKey);
        // then
        assertThat(decodeResult).hasValue(1L);
    }

    @Test
    void signing_key_가_다른_경우_decode_실패해야함() {
        // given
        Long memberId = 1L;
        String tokenByAnotherSigningKey = anotherSigningKeyJwtService.generate(memberId);
        assertThat(tokenByAnotherSigningKey).isNotBlank();
        // when
        Optional<Long> decodeResult = tokenService.decode(tokenByAnotherSigningKey);
        // then
        assertThat(decodeResult).isEmpty();
    }
}