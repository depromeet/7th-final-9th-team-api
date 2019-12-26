package com.depromeet.todo.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.depromeet.todo.application.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class JwtService implements TokenService<Long> {
    private static final String CLAIM_NAME_MEMBER_ID = "memberId";

    private final String tokenIssuer;
    private final String tokenSigningKey;
    private Algorithm algorithm;
    private JWTVerifier jwtVerifier;

    public JwtService(@Value("${jwt.token-issuer}") String tokenIssuer,
                      @Value("${jwt.token-signing-key}") String tokenSigningKey) {
        this.tokenIssuer = tokenIssuer;
        this.tokenSigningKey = tokenSigningKey;
    }

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(tokenSigningKey);
        jwtVerifier = JWT.require(algorithm).build();
    }

    @Override
    public String generate(Long memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");

        return JWT.create()
                .withIssuer(tokenIssuer)
                .withClaim(CLAIM_NAME_MEMBER_ID, memberId)
                .sign(algorithm);
    }

    @Override
    public Optional<Long> decode(String token) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Map<String, Claim> claims = decodedJWT.getClaims();
            Claim idClaim = claims.get(CLAIM_NAME_MEMBER_ID);
            if (idClaim == null) {
                log.warn("Failed to decode jwt token. token: {}, claims:{}", token, claims);
                return Optional.empty();
            }
            return Optional.of(idClaim.asLong());
        } catch (JWTVerificationException ex) {
            log.warn("Invalid access Token. token:" + token, ex);
            return Optional.empty();
        }
    }
}
