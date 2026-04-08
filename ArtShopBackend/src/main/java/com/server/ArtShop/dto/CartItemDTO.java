package com.server.ArtShop.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Integer id;
    private Integer paintingId;
    private String paintingName;
    private int paintingPrice;
    private int quantity;
}
