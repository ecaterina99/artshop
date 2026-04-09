package com.server.ArtShop.repositories;

import com.server.ArtShop.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartIdAndPaintingId(Integer cartId, Integer paintingId);
    Optional<CartItem> findByIdAndCartId(Integer id, Integer cartId);
}
