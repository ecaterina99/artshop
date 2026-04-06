package com.server.ArtShop.services;

import com.server.ArtShop.dto.CreateOrderDTO;
import com.server.ArtShop.dto.OrderDTO;
import com.server.ArtShop.dto.UpdateOrderDTO;
import com.server.ArtShop.models.Order;
import com.server.ArtShop.models.OrderItems;
import com.server.ArtShop.models.Painting;
import com.server.ArtShop.models.User;
import com.server.ArtShop.repositories.OrderItemsRepository;
import com.server.ArtShop.repositories.OrderRepository;
import com.server.ArtShop.repositories.PaintingRepository;
import com.server.ArtShop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final UserRepository userRepository;
    private final PaintingRepository paintingRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository,
                        UserRepository userRepository, PaintingRepository paintingRepository,
                        ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.userRepository = userRepository;
        this.paintingRepository = paintingRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " does not exist"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.CREATED);
        order.setTotalPrice(0);
        Order savedOrder = orderRepository.save(order);

        int totalPrice = 0;
        List<OrderItems> items = new ArrayList<>();

        for (CreateOrderDTO.OrderItemRequest itemRequest : createOrderDTO.getItems()) {
            Painting painting = paintingRepository.findById(itemRequest.getPaintingId())
                    .orElseThrow(() -> new EntityNotFoundException("Painting with id " + itemRequest.getPaintingId() + " does not exist"));

            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setPainting(painting);
            orderItem.setQuantity(itemRequest.getQuantity());
            items.add(orderItem);

            totalPrice += painting.getPrice() * itemRequest.getQuantity();
        }

        orderItemsRepository.saveAll(items);
        savedOrder.setOrderItems(items);
        savedOrder.setTotalPrice(totalPrice);
        Order finalOrder = orderRepository.save(savedOrder);

        return modelMapper.map(finalOrder, OrderDTO.class);
    }

    @Transactional
    public OrderDTO updateOrder(int id, UpdateOrderDTO updateOrderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " does not exist"));

        if (updateOrderDTO.getStatus() != null) {
            order.setStatus(Order.fromString(updateOrderDTO.getStatus()));
        }

        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderDTO.class);
    }

    @Transactional
    public void deleteOrder(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " does not exist"));
        orderRepository.delete(order);
    }
}
