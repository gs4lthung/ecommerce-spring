package com.gemsi.inventoryservice.repository;

import com.gemsi.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
