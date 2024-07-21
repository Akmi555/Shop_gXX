package de.ait_tr.gxx_shop.domain.dto;
/*
@date 21.07.2024
@author Sergey Bugaienko
*/

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;
import java.util.Objects;


public class CartDto {

    private Long id;

    @JsonBackReference
    private CustomerDto customer;

    List<ProductDto> products;

    @Override
    public String toString() {
        return String.format("CartDto : id: %d, customer id: %d, products count:  %d", id, customer.getId(), products == null ? 0 : products.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id) && Objects.equals(customer, cartDto.customer) && Objects.equals(products, cartDto.products);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(customer);
        result = 31 * result + Objects.hashCode(products);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
