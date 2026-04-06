package com.server.ArtShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaintingDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer length;
    private Integer high;
    private String style;
    private Integer price;
    private String medium;
    private String img;
}
