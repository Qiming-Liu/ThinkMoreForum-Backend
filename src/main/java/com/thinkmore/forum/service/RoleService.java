package com.thinkmore.forum.service;

import com.thinkmore.forum.configuration.StaticConfig;
import com.thinkmore.forum.dto.roles.RolesGetDto;
import com.thinkmore.forum.dto.roles.RolesPutDto;
import com.thinkmore.forum.entity.Roles;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.mapper.RolesMapper;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RolesRepository roleRepository;
    private final RolesMapper rolesMapper;
    private final UsersRepository usersRepository;

    public List<RolesGetDto> getAllRoles() {
        return roleRepository.findAll().stream().map(rolesMapper::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public boolean putRole(List<RolesPutDto> rolesPutDtoList) {
        List<Roles> roleNewList = rolesPutDtoList.stream().map(rolesMapper::toEntity).collect(Collectors.toList());
        List<Roles> roleOldList = roleRepository.findAll();

        List<Roles> removeList = roleOldList.stream().filter(roles -> {
            for (Roles roleNew : roleNewList) {
                if (roleNew.getId().equals(roles.getId())) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());

        List<Roles> addList = roleNewList.stream().filter(roles -> {
            if (roles.getId() != null) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        List<Roles> updateList = roleNewList.stream().filter(roles -> {
            if (roles.getId() == null) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        List<List<Users>> usersList = removeList.stream().map(
                (r) -> usersRepository.findByRole(r)).collect(Collectors.toList());

        roleRepository.deleteAll(removeList);
        roleRepository.saveAll(updateList);
        roleRepository.saveAll(addList);

        Roles role = roleRepository.findByRoleName(StaticConfig.DefaultRole).get();
        usersList.stream().map(list -> {
            if (!list.isEmpty()) {
                list.stream().map(users -> {
                    users.setRole(role);
                    return users;
                });
            }
            usersRepository.saveAll(list);
            return list;
        });
        return true;
    }
}
