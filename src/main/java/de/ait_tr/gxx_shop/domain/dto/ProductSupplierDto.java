package de.ait_tr.gxx_shop.domain.dto;
/*
@date 11.07.2024
@author Sergey Bugaienko
*/


import java.util.Objects;

public class ProductSupplierDto {

    private Long id;
    private String title;
    private int quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSupplierDto that = (ProductSupplierDto) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + quantity;
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("ProductSupplierDto: id - %d, title - %s, quantity - %d",
                id, title, quantity);
    }
}
