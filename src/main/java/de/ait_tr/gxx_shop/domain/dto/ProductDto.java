package de.ait_tr.gxx_shop.domain.dto;
/*
@date 11.07.2024
@author Sergey Bugaienko
*/

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(description = "Class that describes Product DTO")
public class ProductDto {

    @Schema(description = "Product unique identifier", example = "777", accessMode = Schema.AccessMode.READ_ONLY)
   private Long id;

    @Schema(description = "Product title", example = "Banana")
    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-z]{2,}$",
            message = "Product title should be at least 3 characters long, start with a capital letter, and contain only letters and space"
    )
    private String title;

    @Schema(description = "Product price", example = "8.50")
    @DecimalMin(value = "1.0", message = "Product price should be greater or equal than 5")
    @DecimalMax(value = "100000.0", inclusive = false, message = "Product price should be less than 100000")
    private BigDecimal price;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(price);
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s",
                id, title, price);
    }
}
