package com.server.ArtShop.config;

import com.server.ArtShop.dto.CartDTO;
import com.server.ArtShop.dto.CartItemDTO;
import com.server.ArtShop.dto.OrderItemsDTO;
import com.server.ArtShop.models.Cart;
import com.server.ArtShop.models.CartItem;
import com.server.ArtShop.models.OrderItems;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Configuration class for setting up the ModelMapper bean.
 * It defines custom mapping behavior.
 */

@Configuration

public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        Converter<Object, Object> hibernateCollectionConverter = new AbstractConverter<Object, Object>() {
            @Override
            protected Object convert(Object source) {
                if (source instanceof PersistentCollection) {
                    PersistentCollection pc = (PersistentCollection) source;
                    if (!pc.wasInitialized()) {
                        return new ArrayList<>();
                    }
                    return new ArrayList<>((Collection<?>) source);
                }
                if (source instanceof Collection) {
                    return new ArrayList<>((Collection<?>) source);
                }
                return source;
            }
        };

        mapper.addConverter(hibernateCollectionConverter);

        TypeMap<CartItem, CartItemDTO> cartItemMap = mapper.createTypeMap(CartItem.class, CartItemDTO.class);
        cartItemMap.addMapping(src -> src.getPainting().getId(), CartItemDTO::setPaintingId);
        cartItemMap.addMapping(src -> src.getPainting().getName(), CartItemDTO::setPaintingName);
        cartItemMap.addMapping(src -> src.getPainting().getPrice(), CartItemDTO::setPaintingPrice);

        TypeMap<OrderItems, OrderItemsDTO> orderItemsMap = mapper.createTypeMap(OrderItems.class, OrderItemsDTO.class);
        orderItemsMap.addMapping(src -> src.getOrder().getId(), OrderItemsDTO::setOrderId);
        orderItemsMap.addMapping(src -> src.getPainting().getId(), OrderItemsDTO::setPaintingId);

        TypeMap<Cart, CartDTO> cartMap = mapper.createTypeMap(Cart.class, CartDTO.class);
        cartMap.addMapping(src -> src.getUser().getId(), CartDTO::setUserId);
        cartMap.addMappings(m -> m.using(ctx -> {
            Cart cart = (Cart) ctx.getSource();
            return cart.getCartItems().stream()
                    .mapToInt(item -> item.getPainting().getPrice() * item.getQuantity())
                    .sum();
        }).map(src -> src, CartDTO::setTotalPrice));
        cartMap.addMappings(m -> m.using(ctx -> {
            @SuppressWarnings("unchecked")
            java.util.List<CartItem> items = (java.util.List<CartItem>) ctx.getSource();
            return items.stream()
                    .map(item -> mapper.map(item, CartItemDTO.class))
                    .toList();
        }).map(Cart::getCartItems, CartDTO::setItems));

        return mapper;
    }
}