package de.ait_tr.gxx_shop.repository;
/*
@date 17.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByTitle(String title);
}
