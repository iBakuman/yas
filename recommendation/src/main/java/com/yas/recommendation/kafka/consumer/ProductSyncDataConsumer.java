package com.yas.recommendation.kafka.consumer;

import com.yas.commonlibrary.kafka.cdc.BaseCdcConsumer;
import com.yas.commonlibrary.kafka.cdc.RetrySupportDql;
import com.yas.commonlibrary.kafka.cdc.message.ProductCdcMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Product synchronize data consumer for pgvector.
 */
@Component
public class ProductSyncDataConsumer extends BaseCdcConsumer<ProductCdcMessage> {

    private final ProductSyncService productSyncService;

    public ProductSyncDataConsumer(ProductSyncService productSyncService) {
        this.productSyncService = productSyncService;
    }

    @KafkaListener(
        id = "product-sync-recommendation",
        groupId = "product-sync",
        topics = "${product.topic.name}",
        containerFactory = "productCdcListenerContainerFactory"
    )
    @RetrySupportDql(listenerContainerFactory = "productCdcListenerContainerFactory")
    public void processMessage(@Payload(required = false) ProductCdcMessage record, @Headers MessageHeaders headers) {
        processMessage(record, headers, productSyncService::sync);
    }
}
