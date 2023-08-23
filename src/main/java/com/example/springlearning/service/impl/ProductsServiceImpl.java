package com.example.springlearning.service.impl;

import com.example.springlearning.exeptins.RecordNotFoundException;
import com.example.springlearning.model.Product;
import com.example.springlearning.repository.ProductRepository;

import com.example.springlearning.service.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductRepository productRepository;

    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> result = productRepository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }
    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).get();
        if(product != null) {
            return product;
        } else {
            throw new RecordNotFoundException("Product not found: id = " + id);
        }
    }
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product , Long id) {
        Product productData = productRepository.findById(id).get();
        if(productData != null) {
            productData.setTitle(product.getTitle());
            productData.setPrice(product.getPrice());
            productRepository.save(productData);
        }
        return productData;
    }
    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).get();
        if(product != null) {
            productRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Product not found: id = " + id);
        }
    }

    @Override
    public Page<Product> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return productRepository.findByKeyword(keyword);
    }
}
