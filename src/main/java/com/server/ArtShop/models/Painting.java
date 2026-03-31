package com.server.ArtShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "painting")
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Name is required")
    private String name;
    private String description;
    @NotNull(message = "Length is required")
    @Min(value = 0, message = "Length cannot be negative")
    private int length;
    @NotNull(message = "High is required")
    @Min(value = 0, message = "High cannot be negative")
    private int high;
    @NotNull(message = "Difficulty level is required")
    @Column(name = "style", nullable = false)
    @Enumerated(EnumType.STRING)
    private Style style;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private int price;
    private String medium;
    private String img;

    @OneToMany(mappedBy = "painting")
    private List<OrderItems> orderItems;

    public enum Style {
        MODERN("modern"),
        ABSTRACTION("abstraction"),
        IMPRESSIONIST("impressionist"),
        MINIMALIST("minimalist"),
        SURREALIST("surrealist");

        private final String displayName;

        Style(String displayName) {
            this.displayName = displayName;
        }
    }

    public static Style fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String result = value.trim().toUpperCase();

        try {
            return Style.valueOf(result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + value +
                    ". Valid values are: modern, abstraction, impressionist, minimalist, surrealist");
        }
    }
}
