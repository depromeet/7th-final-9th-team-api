package com.depromeet.todo.infrastructure.kakao;

import com.depromeet.todo.domain.member.oauth.OAuthAccessToken;
import com.depromeet.todo.domain.member.oauth.OAuthCredential;
import com.depromeet.todo.domain.member.oauth.OAuthService;
import com.depromeet.todo.domain.member.oauth.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoUserService implements OAuthService {
    private final RestTemplate kakaoApiRestTemplate;

    @Override
    public OAuthUserInfo getUserInfo(OAuthCredential oAuthCredential) {
        Assert.notNull(oAuthCredential, "'oAuthCredential' must not be null");
        if (!(oAuthCredential instanceof OAuthAccessToken)) {
            log.error("'It's not supported oauth credential type. oAuthCredential: {}", oAuthCredential);
            throw new IllegalArgumentException("'It's not supported oauth credential type. oAuthCredential: " + oAuthCredential);
        }

        OAuthAccessToken oAuthAccessToken = (OAuthAccessToken) oAuthCredential;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(oAuthAccessToken.getCredential());
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RequestEntity requestEntity = new RequestEntity(
                httpHeaders,
                HttpMethod.GET,
                UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me")
                        .build()
                        .toUri()
        );

        ResponseEntity<Map> responseEntity = kakaoApiRestTemplate.exchange(requestEntity, Map.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get user info from kakao api. response: {}", responseEntity);
            throw new KakaoApiFailedException("Failed to get user info from kakao api. response:" + responseEntity);
        }

        return OAuthUserInfo.fromKakao(
                String.valueOf(
                        Objects.requireNonNull(responseEntity.getBody()).get("id")
                )
        );
    }
}
