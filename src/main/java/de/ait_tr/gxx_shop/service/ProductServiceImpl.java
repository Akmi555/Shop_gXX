package de.ait_tr.gxx_shop.service;
/*
@date 13.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.ProductDto;
import de.ait_tr.gxx_shop.domain.entity.Product;
import de.ait_tr.gxx_shop.exception_handling.exceptions.ThirdTestException;
import de.ait_tr.gxx_shop.repository.ProductRepository;
import de.ait_tr.gxx_shop.service.interfaces.ProductService;
import de.ait_tr.gxx_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService mappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = mappingService.mapDtoToEntity(productDto);
//        product.setActive(true); // Реализовали в mapping.
        return mappingService.mapEntityToDto(repository.save(product));
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        return repository.findAll().stream()
                .filter(Product::isActive)
                .map(mappingService::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null || !product.isActive()) {
            throw new ThirdTestException("This is Third Test Exception");
//            return null;
        }
        return mappingService.mapEntityToDto(product);
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto deleteProductById(Long id) {
        return null;
    }

    @Override
    public ProductDto deleteByTitle(String title) {
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

    @Override
    @Transactional
    public void attachImage(String imageUrl, String productTitle) {
        Product product = repository.findByTitle(productTitle)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Присваиваем ссылку на изображение
        product.setImage(imageUrl);

        // Сохраняем продукт
        repository.save(product);

        // Сохранять явно ничего не нужно, так как продукт в состоянии Managed
        // и изменения автоматически попадут в базу по завершении транзакции.
    }
}
