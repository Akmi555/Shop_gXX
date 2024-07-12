package de.ait_tr.gxx_shop.controller;
/*
@date 11.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        // Todo обращаемся к сервису для сохранения продукта
        return product;
    }

}
