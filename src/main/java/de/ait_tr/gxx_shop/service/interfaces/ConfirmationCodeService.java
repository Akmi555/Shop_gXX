package de.ait_tr.gxx_shop.service.interfaces;
/*
@date 07.08.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.ConfirmationCode;
import de.ait_tr.gxx_shop.domain.entity.User;


public interface ConfirmationCodeService {

    String generateConfirmCode(User user);
    ConfirmationCode findByCode(String code);
}
