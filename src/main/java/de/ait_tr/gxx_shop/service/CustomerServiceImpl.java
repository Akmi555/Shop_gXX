package de.ait_tr.gxx_shop.service;
/*
@date 22.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.dto.CustomerDto;
import de.ait_tr.gxx_shop.service.interfacse.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    //TODO реализовать методы


    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        return null;
    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {
        return List.of();
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        return null;
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        return null;
    }

    @Override
    public void deleteCustomerById(Long id) {

    }

    @Override
    public void deleteCustomerByName(String name) {

    }

    @Override
    public CustomerDto restoreCustomerById(Long id) {
        return null;
    }

    @Override
    public long getActiveCustomerCount() {
        return 0;
    }

    @Override
    public BigDecimal getCartTotalPrice(Long customerId) {
        return null;
    }

    @Override
    public BigDecimal getCartAveragePrice(Long customerId) {
        return null;
    }

    @Override
    public void addProductToCustomerCart(Long customerId, Long productId) {

    }

    @Override
    public void removeProductFromCustomerCart(Long customerId, Long productId) {

    }

    @Override
    public void clearCustomerCart(Long customerId) {

    }
}
