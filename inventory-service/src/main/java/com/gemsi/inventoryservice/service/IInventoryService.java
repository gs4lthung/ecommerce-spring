package com.gemsi.inventoryservice.service;

import com.gemsi.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface IInventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
