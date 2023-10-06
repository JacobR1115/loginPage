package com.login.service;

import com.login.dto.UserDto;
import com.login.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByUsername(String email);

    List<UserDto> findAllUsers();
}
