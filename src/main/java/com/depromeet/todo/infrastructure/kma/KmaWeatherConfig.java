package com.depromeet.todo.infrastructure.kma;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;

@Slf4j
@ConstructorBinding
@ConfigurationProperties(prefix = "weather.kma")
public class KmaWeatherConfig {
    private final String serviceKeyName;
    private final String serviceKeyValue;
    private final Long connectTimeoutMillis;
    private final Long readTimeoutMillis;

    public KmaWeatherConfig(String serviceKeyName,
                            String serviceKeyValue,
                            Long connectTimeoutMillis,
                            Long readTimeoutMillis) {
        this.serviceKeyName = serviceKeyName;
        this.serviceKeyValue = serviceKeyValue;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
    }

    @Bean("kmaRestTemplate")
    public RestTemplate kmaRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(connectTimeoutMillis))
                .setReadTimeout(Duration.ofMillis(readTimeoutMillis))
                .additionalInterceptors(
                        this.serviceKeyInjectionInterceptor()
                )
                .build();
    }

    private ClientHttpRequestInterceptor serviceKeyInjectionInterceptor() {
        return (request, body, execution) -> {
            String query = request.getURI().getQuery();
            if (!StringUtils.isEmpty(query) && query.contains(serviceKeyName)) {
                return execution.execute(request, body);
            }
            URI uri = UriComponentsBuilder.fromUri(request.getURI()).queryParam(serviceKeyName, serviceKeyValue).build(true).toUri();
            return execution.execute(new RequestWrapper(request, uri), body);
        };
    }

    /**
     * HttpRequest 클래스의 uri 값을 변경하기 위한 wrapper class
     */
    private static class RequestWrapper implements HttpRequest {
        private final HttpRequest original;
        private final URI newUriWithParam;

        private RequestWrapper(HttpRequest original, URI newUriWithParam) {
            this.original = original;
            this.newUriWithParam = newUriWithParam;
        }

        @Override
        public String getMethodValue() {
            return original.getMethodValue();
        }

        @Override
        public URI getURI() {
            return newUriWithParam;
        }

        @Override
        public HttpHeaders getHeaders() {
            return original.getHeaders();
        }
    }
}
