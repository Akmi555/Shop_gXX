package de.ait_tr.gxx_shop.controller;

import de.ait_tr.gxx_shop.domain.dto.ProductSupplierDto;
import de.ait_tr.gxx_shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 18.08.2024
 */

@RestController
@RequestMapping("/system")
public class SystemController {

    private final ProductService productService;

    public SystemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductSupplierDto> getAllProducts(){
        return  productService.getAllActiveProductsForSupplier();
    }
}
