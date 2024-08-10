package de.ait_tr.gxx_shop.repository;
/*
@date 17.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
}
