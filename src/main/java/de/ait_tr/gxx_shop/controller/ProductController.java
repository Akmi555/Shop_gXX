package de.ait_tr.gxx_shop.controller;
/*
@date 11.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Product;
import de.ait_tr.gxx_shop.service.interfacse.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    // GET /products?id=3
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        // здесь у нас вполне может вернуться null
        return productService.getById(id);
    }

    //GET -> /products
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllActiveProducts();
    }

    // Update: PUT -> /products/id - и в теле поля, которые мы хотим поменять
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    // Delete: DELETE -> /products/id
    @DeleteMapping("/{productId}")
    public Product remove(@PathVariable("productId") Long id) {
        // Todo Обращаемся к сервису для удаления продукта
        return productService.deleteProductById(id);
    }

    /*
    // Универсальный метод с двумя необязательными параметрами.
    @DeleteMapping
    public Product removeUniversal(@RequestParam(required = false) Long id,@RequestParam(required = false) String title) {
        if (id != null){
            return productService.deleteProductById(id);
        } else if (title != null) {
            return productService.deleteByTitle(title);
        }
        return null;
    }
     */

    @DeleteMapping("/by-title")
    public Product removeByTitle(@RequestParam String title) {
        return productService.deleteByTitle(title);
    }


    @PutMapping("/restore/{id}")
    public void restoreById(@PathVariable Long id) {
        productService.restoreById(id);
    }

    @GetMapping("/count")
    public long getProductCount() {
        return productService.getProductCount();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return productService.getTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return productService.getAveragePrice();
    }

}
