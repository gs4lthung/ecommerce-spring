package com.gemsi.orderservice.service;

import com.gemsi.orderservice.dto.OrderLineItemDto;
import com.gemsi.orderservice.dto.OrderRequest;
import com.gemsi.orderservice.model.Order;
import com.gemsi.orderservice.model.OrderLineItem;
import com.gemsi.orderservice.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapOrderLineItemToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItem mapOrderLineItemToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        return orderLineItem;
    }
}
