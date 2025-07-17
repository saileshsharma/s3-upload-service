
package com.ch.carinspection.imgupl.service;

import com.ch.carinspection.imgupl.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Sends the image ID (as String) to the imageScanQueue for asynchronous scanning.
     *
     * @param imageId The ID of the uploaded image record
     */
    public void sendImageScanMessage(Long imageId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, imageId.toString());
    }

    // Optional overload to support sending full image URL instead of ID
    public void sendImageScanMessage(String imageUrl) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, imageUrl);
    }
}
