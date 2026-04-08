package com.server.ArtShop.controllers;

import com.server.ArtShop.dto.*;
import com.server.ArtShop.exceptions.ApiError;
import com.server.ArtShop.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Cart", description = "Shopping cart management")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Get current cart")
    @ApiResponse(responseCode = "200", description = "Cart retrieved successfully")
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(cartService.getCart(jwt.getClaimAsString("email")));
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart")
    @ApiResponse(responseCode = "200", description = "Item added to cart")
    @ApiResponse(responseCode = "404", description = "Painting not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<CartDTO> addItem(@AuthenticationPrincipal Jwt jwt,
                                           @Valid @RequestBody AddToCartDTO addToCartDTO) {
        return ResponseEntity.ok(cartService.addItem(jwt.getClaimAsString("email"), addToCartDTO));
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update cart item quantity")
    @ApiResponse(responseCode = "200", description = "Cart item updated")
    @ApiResponse(responseCode = "404", description = "Cart item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<CartDTO> updateItemQuantity(@AuthenticationPrincipal Jwt jwt,
                                                      @PathVariable int itemId,
                                                      @Valid @RequestBody UpdateCartItemDTO updateDTO) {
        return ResponseEntity.ok(cartService.updateItemQuantity(jwt.getClaimAsString("email"), itemId, updateDTO));
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remove item from cart")
    @ApiResponse(responseCode = "200", description = "Cart item removed")
    @ApiResponse(responseCode = "404", description = "Cart item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<CartDTO> removeItem(@AuthenticationPrincipal Jwt jwt,
                                              @PathVariable int itemId) {
        return ResponseEntity.ok(cartService.removeItem(jwt.getClaimAsString("email"), itemId));
    }

    @DeleteMapping
    @Operation(summary = "Clear cart")
    @ApiResponse(responseCode = "204", description = "Cart cleared")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Jwt jwt) {
        cartService.clearCart(jwt.getClaimAsString("email"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout cart", description = "Converts cart items into an order and clears the cart")
    @ApiResponse(responseCode = "201", description = "Order created from cart")
    @ApiResponse(responseCode = "400", description = "Cart is empty",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<OrderDTO> checkout(@AuthenticationPrincipal Jwt jwt) {
        OrderDTO order = cartService.checkout(jwt.getClaimAsString("email"));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
