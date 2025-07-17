package com.ch.carinspection.imgupl.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ setup.
 * <p>
 * Defines the durable queue used for image scan message processing.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Name of the RabbitMQ queue for image scan messages.
     */
    public static final String QUEUE_NAME = "imageScanQueue";

    /**
     * Declares the RabbitMQ queue bean.
     * The queue is durable to survive broker restarts.
     *
     * @return the configured durable Queue instance
     */
    @Bean
    public Queue imageScanQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
