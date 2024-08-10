package de.ait_tr.gxx_shop.service;
/*
@date 07.08.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.repository.UserRepository;
import de.ait_tr.gxx_shop.service.interfaces.EmailService;
import de.ait_tr.gxx_shop.service.interfaces.RoleService;
import de.ait_tr.gxx_shop.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleService roleService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    @Override
    public void register(User user) {
        // Проверка, существует ли уже пользователь с таким email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        //присваиваем новому пользователю роль "USER" по умолчанию, используя `RoleService`
        user.setRoles(Set.of(roleService.getRoleUser()));

        userRepository.save(user);

        // после успешного сохранения, мы отправляем пользователю письмо с кодом подтверждения
        emailService.sendConfirmationEmail(user);

    }
}
