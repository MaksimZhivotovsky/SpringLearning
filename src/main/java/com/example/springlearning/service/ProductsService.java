package com.example.springlearning.service;

import com.example.springlearning.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductsService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product save(Product product);
    void deleteById(Long id);
    Page<Product> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection);
    List<Product> findByKeyword(String keyword);
}
