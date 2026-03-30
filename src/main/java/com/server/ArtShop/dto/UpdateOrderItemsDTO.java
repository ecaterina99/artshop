package com.server.ArtShop.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderItemsDTO {
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
