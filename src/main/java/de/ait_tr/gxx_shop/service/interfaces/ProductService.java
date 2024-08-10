package de.ait_tr.gxx_shop.service.interfaces;
/*
@date 13.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);
    List<ProductDto> getAllActiveProducts();
    ProductDto getById(Long id);
    ProductDto update(Long id, ProductDto productDto);
    ProductDto deleteProductById(Long id);


    // Остальные 5
    ProductDto deleteByTitle(String title);
    void restoreById(Long id);
    long getProductCount();
    BigDecimal getTotalPrice();
    BigDecimal getAveragePrice();
}
