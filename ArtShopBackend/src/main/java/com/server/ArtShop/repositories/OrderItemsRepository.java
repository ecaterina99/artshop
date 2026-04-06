package com.server.ArtShop.repositories;

import com.server.ArtShop.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer> {
}
