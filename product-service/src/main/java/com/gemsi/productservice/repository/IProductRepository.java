package com.gemsi.productservice.repository;

import com.gemsi.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductRepository extends MongoRepository<Product, String> {

}
