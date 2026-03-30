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
public class UpdatePaintingDTO {
    private String name;
    private String description;
    @Min(value = 0, message = "Length cannot be negative")
    private Integer length;
    @Min(value = 0, message = "High cannot be negative")
    private Integer high;
    private String style;
    @Min(value = 0, message = "Price cannot be negative")
    private Integer price;
    private String medium;
    private String img;
}
