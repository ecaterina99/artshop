package com.server.ArtShop.controllers;

import com.server.ArtShop.dto.CreateOrderItemsDTO;
import com.server.ArtShop.dto.OrderItemsDTO;
import com.server.ArtShop.dto.UpdateOrderItemsDTO;
import com.server.ArtShop.exceptions.ApiError;
import com.server.ArtShop.services.OrderItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@Tag(name = "Order Items", description = "Order items management")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;
    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @GetMapping
    @Operation(summary = "Get all order items")
    @ApiResponse(responseCode = "200", description = "Order items retrieved successfully")
    public ResponseEntity<List<OrderItemsDTO>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemsService.getAllOrderItems());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order item by ID")
    @ApiResponse(responseCode = "200", description = "Order item found")
    @ApiResponse(responseCode = "404", description = "Order item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<OrderItemsDTO> getOrderItemById(@PathVariable int id) {
        return ResponseEntity.ok(orderItemsService.getOrderItemById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new order item", description = "Adds a painting to an order with specified quantity")
    @ApiResponse(responseCode = "201", description = "Order item created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "Order or painting not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<OrderItemsDTO> createOrderItem(@Valid @RequestBody CreateOrderItemsDTO createDTO) {
        OrderItemsDTO created = orderItemsService.createOrderItem(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order item quantity")
    @ApiResponse(responseCode = "200", description = "Order item updated successfully")
    @ApiResponse(responseCode = "404", description = "Order item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "400", description = "Invalid quantity",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<OrderItemsDTO> updateOrderItem(@PathVariable int id, @Valid @RequestBody UpdateOrderItemsDTO updateDTO) {
        OrderItemsDTO updated = orderItemsService.updateOrderItem(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order item")
    @ApiResponse(responseCode = "204", description = "Order item deleted successfully")
    @ApiResponse(responseCode = "404", description = "Order item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<Void> deleteOrderItem(@PathVariable int id) {
        orderItemsService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
