package com.yas.search.config;

import com.yas.search.container.AppKafkaContainer;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;

@TestConfiguration
public class KafkaIntegrationTestConfiguration {

    @Bean(destroyMethod = "stop")
    public KeycloakContainer keycloakContainer(DynamicPropertyRegistry registry) {
        KeycloakContainer keycloak = new KeycloakContainer()
            .withRealmImportFiles("/test-realm.json")
            .withReuse(true);

        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
            () -> keycloak.getAuthServerUrl() + "/realms/quarkus");
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
            () -> keycloak.getAuthServerUrl() + "/realms/quarkus/protocol/openid-connect/certs");
        return keycloak;
    }

    @Bean
    public AppKafkaContainer kafkaContainer() {
        return new AppKafkaContainer();
    }
}
