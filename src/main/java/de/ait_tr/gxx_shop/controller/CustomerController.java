package de.ait_tr.gxx_shop.controller;
/*
@date 21.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.CustomerDto;
import de.ait_tr.gxx_shop.service.interfaces.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.saveCustomer(customerDto);
    }


    @GetMapping
    public List<CustomerDto> getAllActiveCustomers() {
        return customerService.getAllActiveCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    @DeleteMapping("/name/{name}")
    public void deleteCustomerByName(@PathVariable String name) {
        customerService.deleteCustomerByName(name);
    }

    @PutMapping("/restore/{id}")
    public CustomerDto restoreCustomerById(@PathVariable Long id) {
        return customerService.restoreCustomerById(id);
    }

    @GetMapping("/count")
    public long getActiveCustomerCount() {
        return customerService.getActiveCustomerCount();
    }

    @GetMapping("/{id}/cart/total-price")
    public BigDecimal getCartTotalPrice(@PathVariable("id") Long customerId) {
        return customerService.getCartTotalPrice(customerId);
    }

    @GetMapping("/{id}/cart/average-price")
    public BigDecimal getCartAveragePrice(@PathVariable("id") Long customerId) {
        return customerService.getCartAveragePrice(customerId);
    }

    @PostMapping("/{customerId}/cart/{productId}")
    public void addProductToCustomerCart(@PathVariable Long customerId, @PathVariable Long productId) {
        customerService.addProductToCustomerCart(customerId, productId);
    }

    @DeleteMapping("/{customerId}/cart/{productId}")
    public void removeProductFromCustomerCart(@PathVariable Long customerId, @PathVariable Long productId) {
        customerService.removeProductFromCustomerCart(customerId, productId);
    }

    @GetMapping("/{id}/cart/clear")
    public void clearCustomerCart(@PathVariable("id")Long customerId) {
        customerService.clearCustomerCart(customerId);
    }
}
