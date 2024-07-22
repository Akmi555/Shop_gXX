package de.ait_tr.gxx_shop.domain.entity;
/*
@date 21.07.2024
@author Sergey Bugaienko
*/

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> products;

    @NotNull
    private String name;


    @Override
    public String toString() {
        return String.format("Cart: id: %d, products count:  %d", id, products == null ? 0 : products.size());
    }

    // Добавление продукта, если он активный
    public void addProduct(Product product) {
        if (product.isActive()) {
            products.add(product);
        }
    }

    // Получение всех продуктов, находящихся в корзине (активных)
    public List<Product> getAllActiveProducts() {
        return products.stream()
                .filter(Product::isActive)
                .toList();
    }

    //Удалить продукт из корзины по его идентификатору
    public Product removeById(Long id) {
        // products.removeIf(pr -> pr.getId().equals(id)); - Если в корзине 10 бананов. Удалит ВСЕ. А мы хотим удалить один из корзины
        Optional<Product> optionalProduct = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (optionalProduct.isEmpty()) return null;
        Product product = optionalProduct.get();
        products.remove(product);
        return product;
    }

    // Полная очистка корзины (удаление всех продуктов)
    public void clear(){
        products.clear();
    }

    // Получение общей стоимости корзины (активных продуктов)
    public BigDecimal getTotalPrice() {
        if (products == null) return BigDecimal.ZERO;

        return products.stream()
                .filter(Product::isActive)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Получение средней стоимости товара в корзине (из активных продуктов)
    public BigDecimal getAveragePrice() {

        long countActive = products.stream()
                .filter(Product::isActive)
                .count();

        if (products == null || countActive == 0) return BigDecimal.ZERO;

        return getTotalPrice().divide(new BigDecimal(countActive), RoundingMode.HALF_UP);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(customer, cart.customer) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(customer);
        result = 31 * result + Objects.hashCode(products);
        return result;
    }
}
