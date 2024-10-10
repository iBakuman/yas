package com.yas.search.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.yas.commonlibrary.kafka.cdc.message.ProductCdcMessage;
import com.yas.search.constant.Action;
import com.yas.search.kafka.consumer.ProductSyncDataConsumer;
import com.yas.commonlibrary.kafka.cdc.message.Product;
import com.yas.search.service.ProductSyncDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductSyncDataConsumerTest {

    @InjectMocks
    private ProductSyncDataConsumer productSyncDataConsumer;

    @Mock
    private ProductSyncDataService productSyncDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSync_whenCreateAction_createProduct() {
        // When
        long productId = 1L;
        productSyncDataConsumer.sync(
            ProductCdcMessage.builder()
                .after(Product.builder().id(productId).build())
                .op(Action.CREATE)
                .build()
        );

        // Then
        verify(productSyncDataService, times(1)).createProduct(productId);
    }

    @Test
    void testSync_whenUpdateAction_updateProduct() {
        // When
        long productId = 2L;
        productSyncDataConsumer.sync(
            ProductCdcMessage.builder()
                .after(Product.builder().id(productId).build())
                .op(Action.UPDATE)
                .build()
        );

        // Then
        verify(productSyncDataService, times(1)).updateProduct(productId);
    }

    @Test
    void testSync_whenDeleteAction_deleteProduct() {
        // When
        final long productId = 3L;
        productSyncDataConsumer.sync(
            ProductCdcMessage.builder()
                .after(Product.builder().id(productId).build())
                .op(Action.DELETE)
                .build()
        );

        // Then
        verify(productSyncDataService, times(1)).deleteProduct(productId);
    }

    @Test
    void testSync_whenInvalidAction_noAction() {
        // When
        productSyncDataConsumer.sync(ProductCdcMessage.builder().op("INVALID_ACTION").build());

        // Then
        verify(productSyncDataService, times(0)).createProduct(any());
        verify(productSyncDataService, times(0)).updateProduct(any());
        verify(productSyncDataService, times(0)).deleteProduct(any());
    }
}
