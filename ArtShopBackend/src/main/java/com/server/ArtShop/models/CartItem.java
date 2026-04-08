package com.server.ArtShop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "cart_items")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "painting_id"}))
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "painting_id", nullable = false)
    private Painting painting;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
