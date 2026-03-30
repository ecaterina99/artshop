package com.server.ArtShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDTO {
    private Integer id;
    private Integer orderId;
    private Integer paintingId;
    private Integer quantity;
}
