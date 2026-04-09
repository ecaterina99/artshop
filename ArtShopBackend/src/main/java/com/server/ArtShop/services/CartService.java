package com.server.ArtShop.services;

import com.server.ArtShop.dto.*;
import com.server.ArtShop.models.*;
import com.server.ArtShop.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final PaintingRepository paintingRepository;
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       UserRepository userRepository, PaintingRepository paintingRepository,
                       OrderRepository orderRepository, OrderItemsRepository orderItemsRepository,
                       ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.paintingRepository = paintingRepository;
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.modelMapper = modelMapper;
    }

    private User findOrCreateUser(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                });
    }

    @Transactional(readOnly = true)
    public CartDTO getCart(String email) {
        return cartRepository.findByUserEmail(email)
                .map(cart -> modelMapper.map(cart, CartDTO.class))
                .orElseGet(() -> {
                    CartDTO empty = new CartDTO();
                    empty.setItems(List.of());
                    empty.setTotalPrice(0);
                    return empty;
                });
    }

    @Transactional
    public CartDTO addItem(String email, AddToCartDTO dto) {
        User user = findOrCreateUser(email);

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Painting painting = paintingRepository.findById(dto.getPaintingId())
                .orElseThrow(() -> new EntityNotFoundException("Painting with id " + dto.getPaintingId() + " does not exist"));

        CartItem existingItem = cartItemRepository.findByCartIdAndPaintingId(cart.getId(), painting.getId())
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + dto.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setPainting(painting);
            newItem.setQuantity(dto.getQuantity());
            cartItemRepository.save(newItem);
        }

        return modelMapper.map(cartRepository.findById(cart.getId()).orElseThrow(), CartDTO.class);
    }

    @Transactional
    public CartDTO updateItemQuantity(String email, int cartItemId, UpdateCartItemDTO dto) {
        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart item with id " + cartItemId + " does not exist in your cart"));

        item.setQuantity(dto.getQuantity());
        cartItemRepository.save(item);

        return modelMapper.map(cartRepository.findById(cart.getId()).orElseThrow(), CartDTO.class);
    }

    @Transactional
    public CartDTO removeItem(String email, int cartItemId) {
        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findByIdAndCartId(cartItemId, cart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart item with id " + cartItemId + " does not exist in your cart"));

        cart.getCartItems().remove(item);
        cartRepository.save(cart);

        return modelMapper.map(cartRepository.findById(cart.getId()).orElseThrow(), CartDTO.class);
    }

    @Transactional
    public void clearCart(String email) {
        cartRepository.findByUserEmail(email).ifPresent(cart -> {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        });
    }

    @Transactional
    public OrderDTO checkout(String email) {
        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }

        User user = cart.getUser();

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.CREATED);
        order.setTotalPrice(0);
        Order savedOrder = orderRepository.save(order);

        long totalPrice = 0;
        List<OrderItems> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setPainting(cartItem.getPainting());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);

            totalPrice += (long) cartItem.getPainting().getPrice() * cartItem.getQuantity();
        }

        orderItemsRepository.saveAll(orderItems);
        savedOrder.setOrderItems(orderItems);
        savedOrder.setTotalPrice(totalPrice);
        Order finalOrder = orderRepository.save(savedOrder);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return modelMapper.map(finalOrder, OrderDTO.class);
    }
}
