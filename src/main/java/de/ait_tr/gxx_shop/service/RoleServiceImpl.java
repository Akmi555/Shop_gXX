package de.ait_tr.gxx_shop.service;
/*
@date 07.08.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Role;
import de.ait_tr.gxx_shop.repository.RoleRepository;
import de.ait_tr.gxx_shop.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role getRoleUser() {
        // Получаем роль "USER" из базы данных
        return roleRepository.findByTitle("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Database don't contains role User"));
    }
}
