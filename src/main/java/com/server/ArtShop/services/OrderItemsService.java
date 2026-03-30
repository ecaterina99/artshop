package com.server.ArtShop.services;

import com.server.ArtShop.dto.CreateOrderItemsDTO;
import com.server.ArtShop.dto.OrderItemsDTO;
import com.server.ArtShop.dto.UpdateOrderItemsDTO;
import com.server.ArtShop.models.Order;
import com.server.ArtShop.models.OrderItems;
import com.server.ArtShop.models.Painting;
import com.server.ArtShop.repositories.OrderItemsRepository;
import com.server.ArtShop.repositories.OrderRepository;
import com.server.ArtShop.repositories.PaintingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final OrderRepository orderRepository;
    private final PaintingRepository paintingRepository;
    private final ModelMapper modelMapper;

    public OrderItemsService(OrderItemsRepository orderItemsRepository, OrderRepository orderRepository,
                             PaintingRepository paintingRepository, ModelMapper modelMapper) {
        this.orderItemsRepository = orderItemsRepository;
        this.orderRepository = orderRepository;
        this.paintingRepository = paintingRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderItemsDTO> getAllOrderItems() {
        return orderItemsRepository.findAll().stream()
                .map(item -> modelMapper.map(item, OrderItemsDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderItemsDTO getOrderItemById(int id) {
        OrderItems item = orderItemsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with id " + id + " does not exist"));
        return modelMapper.map(item, OrderItemsDTO.class);
    }

    @Transactional
    public OrderItemsDTO createOrderItem(CreateOrderItemsDTO createDTO) {
        Order order = orderRepository.findById(createDTO.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + createDTO.getOrderId() + " does not exist"));

        Painting painting = paintingRepository.findById(createDTO.getPaintingId())
                .orElseThrow(() -> new EntityNotFoundException("Painting with id " + createDTO.getPaintingId() + " does not exist"));

        OrderItems item = new OrderItems();
        item.setOrder(order);
        item.setPainting(painting);
        item.setQuantity(createDTO.getQuantity());

        OrderItems saved = orderItemsRepository.save(item);
        return modelMapper.map(saved, OrderItemsDTO.class);
    }

    @Transactional
    public OrderItemsDTO updateOrderItem(int id, UpdateOrderItemsDTO updateDTO) {
        OrderItems item = orderItemsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with id " + id + " does not exist"));

        if (updateDTO.getQuantity() != null) {
            item.setQuantity(updateDTO.getQuantity());
        }

        OrderItems saved = orderItemsRepository.save(item);
        return modelMapper.map(saved, OrderItemsDTO.class);
    }

    @Transactional
    public void deleteOrderItem(int id) {
        OrderItems item = orderItemsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with id " + id + " does not exist"));
        orderItemsRepository.delete(item);
    }
}
