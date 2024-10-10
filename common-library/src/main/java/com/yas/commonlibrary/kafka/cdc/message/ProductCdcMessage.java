package com.yas.commonlibrary.kafka.cdc.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCdcMessage {

    private Product after;

    private Product before;

    private String op;

}
