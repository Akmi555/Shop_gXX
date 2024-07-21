package de.ait_tr.gxx_shop.domain.dto;
/*
@date 14.07.2024
@author Sergey Bugaienko
*/

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Objects;


public class CustomerDto {

    private Long id;

    private String name;

    @JsonManagedReference
    private CartDto cart;

    @Override
    public String toString() {
        return String.format("CustomerDto: id - %d, name - %s, Cart  - %s",
                id, name, cart == null ? "null" : cart);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(cart);
        return result;
    }
}
