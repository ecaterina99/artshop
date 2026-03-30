package com.server.ArtShop.Controllers;

import com.server.ArtShop.dto.CreateOrderItemsDTO;
import com.server.ArtShop.dto.OrderItemsDTO;
import com.server.ArtShop.dto.UpdateOrderItemsDTO;
import com.server.ArtShop.services.OrderItemsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemsDTO>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemsService.getAllOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsDTO> getOrderItemById(@PathVariable int id) {
        return ResponseEntity.ok(orderItemsService.getOrderItemById(id));
    }

    @PostMapping
    public ResponseEntity<OrderItemsDTO> createOrderItem(@Valid @RequestBody CreateOrderItemsDTO createDTO) {
        OrderItemsDTO created = orderItemsService.createOrderItem(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemsDTO> updateOrderItem(@PathVariable int id, @Valid @RequestBody UpdateOrderItemsDTO updateDTO) {
        OrderItemsDTO updated = orderItemsService.updateOrderItem(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable int id) {
        orderItemsService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
