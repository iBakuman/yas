package com.yas.search.kafka.consumer;

import com.yas.commonlibrary.kafka.cdc.BaseCdcConsumer;
import com.yas.commonlibrary.kafka.cdc.RetrySupportDql;
import com.yas.commonlibrary.kafka.cdc.message.ProductCdcMessage;
import com.yas.search.constant.Action;
import com.yas.search.service.ProductSyncDataService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Product synchronize data consumer for elasticsearch.
 */
@Service
public class ProductSyncDataConsumer extends BaseCdcConsumer<ProductCdcMessage> {

    private final ProductSyncDataService productSyncDataService;

    public ProductSyncDataConsumer(ProductSyncDataService productSyncDataService) {
        this.productSyncDataService = productSyncDataService;
    }

    @KafkaListener(
        id = "product-sync-es",
        groupId = "product-sync-search",
        topics = "${product.topic.name}",
        containerFactory = "productCdcListenerContainerFactory"
    )
    @RetrySupportDql(listenerContainerFactory = "productCdcListenerContainerFactory")
    public void processMessage(@Payload(required = false) ProductCdcMessage record, @Headers MessageHeaders headers) {
        processMessage(record, headers, this::sync);
    }

    public void sync(ProductCdcMessage productCdcMessage) {
        if (productCdcMessage.getAfter() != null) {
            var action = productCdcMessage.getOp();
            var productId = productCdcMessage.getAfter().getId();
            switch (action) {
                case Action.CREATE, Action.READ:
                    productSyncDataService.createProduct(productId);
                    break;
                case Action.UPDATE:
                    productSyncDataService.updateProduct(productId);
                    break;
                case Action.DELETE:
                    productSyncDataService.deleteProduct(productId);
                    break;
                default:
                    break;
            }
        }
    }
}
