package io.github.aplaraujo.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@DataJpaTest
@EnableJpaRepositories(basePackages = "io.github.aplaraujo.repositories")
@EntityScan(basePackages = "io.github.aplaraujo.entities")
@EnableJpaAuditing
public class BaseRepositoryTest {
    @TestConfiguration
    static class AuditingConfig {
        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("test");
        }
    }
}
