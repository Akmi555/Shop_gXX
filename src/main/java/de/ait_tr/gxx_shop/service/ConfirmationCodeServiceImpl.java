package de.ait_tr.gxx_shop.service;
/*
@date 07.08.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.ConfirmationCode;
import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.repository.ConfirmationCodeRepository;
import de.ait_tr.gxx_shop.service.interfaces.ConfirmationCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private final ConfirmationCodeRepository codeRepository;

    public ConfirmationCodeServiceImpl(ConfirmationCodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public String generateConfirmCode(User user) {
        // Генерация уникального кода (используем UUID)
        String code = UUID.randomUUID().toString();

        // Создание объекта ConfirmationCode и его сохранение в базу данных
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setCode(code);
        confirmationCode.setUser(user);
        confirmationCode.setExpired(LocalDateTime.now().plusDays(1)); // Устанавливаем срок действия кода
        // confirmationCode.setExpired(LocalDateTime.now().plusMinutes(2)); // Устанавливаем срок действия кода для тестирования в ДЗ

        codeRepository.save(confirmationCode); // Сохранение кода в базу данных

        return code; // Возвращаем сгенерированный код
    }

    @Override
    public ConfirmationCode findByCode(String code) {
        return codeRepository.findByCode(code).orElseThrow(
                () -> new RuntimeException("Could not find confirmation code with code: " + code)
        );
    }
}
