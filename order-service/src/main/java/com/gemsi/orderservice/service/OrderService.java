package com.gemsi.orderservice.service;

import com.gemsi.orderservice.dto.InventoryResponse;
import com.gemsi.orderservice.dto.OrderLineItemDto;
import com.gemsi.orderservice.dto.OrderRequest;
import com.gemsi.orderservice.model.Order;
import com.gemsi.orderservice.model.OrderLineItem;
import com.gemsi.orderservice.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapOrderLineItemToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItem::getSkuCode)
                .toList();
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventories",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCode", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays
                .stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }

    }

    private OrderLineItem mapOrderLineItemToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        return orderLineItem;
    }
}
