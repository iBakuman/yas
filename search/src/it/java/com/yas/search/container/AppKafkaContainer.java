package com.yas.search.container;

import lombok.Getter;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@Getter
public class AppKafkaContainer {

    @Container
    static KafkaContainer kafkaContainer;

    public AppKafkaContainer() {
        kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.0.9")
        );
        kafkaContainer.start();
    }
}
