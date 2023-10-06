package com.login.service;

import com.login.dto.UserDto;
import com.login.entity.Role;
import com.login.entity.User;
import com.login.repository.RoleRepo;
import com.login.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepo.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepo.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepo.save(role);
    }
}
