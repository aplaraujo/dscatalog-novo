package io.github.aplaraujo.config;

import io.github.aplaraujo.tests.TokenUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public TokenUtil tokenUtil() {
        return new TokenUtil();
    }
}
