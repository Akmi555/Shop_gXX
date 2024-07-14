package de.ait_tr.gxx_shop.controller;
/*
@date 11.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Product;
import de.ait_tr.gxx_shop.service.interfacse.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Controller for operations with products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create product", description = "Add new product.", tags = { "Product" })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = Product.class)) }) })
    @PostMapping
    public Product saveProduct(@Parameter(description = "Created project object") @RequestBody Product product) {
        return productService.save(product);
    }

    // GET /products?id=3

    @Operation(summary = "Get product by id", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })


    @GetMapping("/{id}")
    public Product getById(
            @Parameter(description = "The id that needs to be fetched. ", required = true) @PathVariable Long id) {
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
