package de.ait_tr.gxx_shop.controller;
/*
@date 11.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.ProductDto;
import de.ait_tr.gxx_shop.exception_handling.Response;
import de.ait_tr.gxx_shop.exception_handling.exceptions.FirstTestException;
import de.ait_tr.gxx_shop.service.interfacse.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Controller for operations with products")
public class ProductController {

    private final ProductService productService;

    /*

У нас будут три уровня доступа:

1. **Получение всех продуктов**:
    - Доступно всем пользователям, включая анонимных.
    - Анонимные пользователи — это те, кто обращается к приложению без логина и пароля.

2. **Получение продукта по идентификатору (ID)**:
    - Доступно только аутентифицированным пользователям с любой ролью.
    - Пользователь должен быть залогинен, чтобы получить доступ к продукту по ID.

3. **Сохранение продукта в базу данных**:
    - Доступно только администраторам.
    - Пользователь должен иметь роль администратора, чтобы сохранить продукт в базу данных.

     */

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create product", description = "Add new product.", tags = { "Product" })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDto.class)) }) })
    @PostMapping
    public ProductDto saveProduct(
            @Parameter(description = "Created project object")
            @Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    // GET /products?id=3

    @Operation(summary = "Get product by id", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content) })


    @GetMapping("/{id}")
    public ProductDto getById(
            @Parameter(description = "The id that needs to be fetched. ", required = true)
            @PathVariable Long id) {
        // здесь у нас вполне может вернуться null
        return productService.getById(id);
    }

    //GET -> /products
    @GetMapping
    public List<ProductDto> getAll() {
        return productService.getAllActiveProducts();
    }

    // Update: PUT -> /products/id - и в теле поля, которые мы хотим поменять
    @PutMapping("/{id}")
    public ProductDto update(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }

    // Delete: DELETE -> /products/id
    @DeleteMapping("/{productId}")
    public ProductDto remove(@PathVariable("productId") Long id) {
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
    public ProductDto removeByTitle(@RequestParam String title) {
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

    @ExceptionHandler(FirstTestException.class)
    public ResponseEntity<Response> handleFirstException(FirstTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
