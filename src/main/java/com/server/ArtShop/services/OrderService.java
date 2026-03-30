package com.server.ArtShop.services;

import com.server.ArtShop.dto.CreateOrderDTO;
import com.server.ArtShop.dto.OrderDTO;
import com.server.ArtShop.dto.UpdateOrderDTO;
import com.server.ArtShop.models.Order;
import com.server.ArtShop.models.User;
import com.server.ArtShop.repositories.OrderRepository;
import com.server.ArtShop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
        User user = userRepository.findById(createOrderDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + createOrderDTO.getUserId() + " does not exist"));

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(0);
        order.setStatus(Order.Status.CREATED);

        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderDTO.class);
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
