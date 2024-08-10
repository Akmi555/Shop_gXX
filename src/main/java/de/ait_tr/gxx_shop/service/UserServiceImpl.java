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
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;
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
    @Transactional
    public void register(User user) {
        // Проверка, существует ли уже пользователь с таким email
        if (userRepository.existsByEmail(user.getEmail())
                && userRepository.findByEmail(user.getEmail()).get().isActive()) {
            throw new RuntimeException("Email is already in use");
        }

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user.setId(null);
            user.setRoles(Set.of(roleService.getRoleUser()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        //присваиваем новому пользователю роль "USER" по умолчанию, используя `RoleService`

        userRepository.save(user);

        // после успешного сохранения, мы отправляем пользователю письмо с кодом подтверждения
        emailService.sendConfirmationEmail(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
