package mtmh.kafka.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateTopicConfig {

    @Bean
    public NewTopic kafkaTestEventTopic() {
        return TopicBuilder.name("appointment-event")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
