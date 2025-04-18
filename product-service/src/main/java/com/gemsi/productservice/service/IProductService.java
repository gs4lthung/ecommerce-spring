package com.gemsi.productservice.service;

import com.gemsi.productservice.dto.ProductRequest;
import com.gemsi.productservice.dto.ProductResponse;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getAllProducts();

    void createProduct(ProductRequest productRequest);

}
