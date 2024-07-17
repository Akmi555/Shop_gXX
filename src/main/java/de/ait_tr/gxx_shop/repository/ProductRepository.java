package de.ait_tr.gxx_shop.repository;
/*
@date 14.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

}
