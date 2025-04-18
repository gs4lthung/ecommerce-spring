package com.gemsi.orderservice.repository;

import com.gemsi.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {

}
