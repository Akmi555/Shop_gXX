package de.ait_tr.gxx_shop.repository;
/*
@date 07.08.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    Optional<ConfirmationCode> findByCode(String code);
}
