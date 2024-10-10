package com.yas.recommendation.kafka.consumer;

import com.yas.commonlibrary.kafka.cdc.message.ProductCdcMessage;
import com.yas.recommendation.constant.Action;
import com.yas.recommendation.vector.product.service.ProductVectorSyncService;
import org.springframework.stereotype.Service;

@Service
public class ProductSyncService {

    private final ProductVectorSyncService productVectorSyncService;

    public ProductSyncService(ProductVectorSyncService productVectorSyncService) {
        this.productVectorSyncService = productVectorSyncService;
    }

    public void sync(ProductCdcMessage record) {
        if (record.getAfter() != null) {
            var action = record.getOp();
            var product = record.getAfter();
            switch (action) {
                case Action.CREATE, Action.READ:
                    productVectorSyncService.createProductVector(product);
                    break;
                case Action.UPDATE:
                    productVectorSyncService.updateProductVector(product);
                    break;
                case Action.DELETE:
                    productVectorSyncService.deleteProductVector(product.getId());
                    break;
                default:
                    break;
            }
        }
    }

}
