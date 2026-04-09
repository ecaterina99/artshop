package com.server.ArtShop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price", nullable = false)
    private long totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public enum Status {
        CREATED("created"),
        PAID("paid"),
        SHIPPED("shipped"),
        DELIVERED("delivered");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }
    }

    public static Status fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String result = value.trim().toUpperCase();

        try {
            return Status.valueOf(result);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + value +
                    ". Valid values are: created, paid, shipped, delivered");
        }
    }
}
