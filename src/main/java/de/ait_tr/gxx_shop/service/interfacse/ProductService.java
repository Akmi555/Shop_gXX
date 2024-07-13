package de.ait_tr.gxx_shop.service.interfacse;
/*
@date 13.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product save(Product product);
    List<Product> getAllActiveProducts();
    Product getById(Long id);
    Product update(Long id, Product product);
    Product deleteProductById(Long id);


    // Остальные 5
    Product deleteByTitle(String title);
    void restoreById(Long id);
    long getProductCount();
    BigDecimal getTotalPrice();
    BigDecimal getAveragePrice();

}
