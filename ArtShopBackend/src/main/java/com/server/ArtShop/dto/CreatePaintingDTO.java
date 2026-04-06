package com.server.ArtShop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaintingDTO {
    @NotNull(message = "Name is required")
    private String name;
    private String description;
    @NotNull(message = "Length is required")
    @Min(value = 0, message = "Length cannot be negative")
    private Integer length;
    @NotNull(message = "High is required")
    @Min(value = 0, message = "High cannot be negative")
    private Integer high;
    @NotNull(message = "Style is required")
    private String style;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Integer price;
    private String medium;
    private String img;
}
