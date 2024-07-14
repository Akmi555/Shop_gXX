package de.ait_tr.gxx_shop.service;
/*
@date 13.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Product;
import de.ait_tr.gxx_shop.repository.ProductRepository;
import de.ait_tr.gxx_shop.service.interfacse.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        product.setActive(true);
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return repository.findAll().stream()
                .filter(Product::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public Product getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null || !product.isActive()) {
            return null;
        }
        return product;
    }

    @Override
    public Product update(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public Product deleteByTitle(String title) {
        return null;
    }

    @Override
    public void restoreById(Long id) {

    }

    @Override
    public long getProductCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}
