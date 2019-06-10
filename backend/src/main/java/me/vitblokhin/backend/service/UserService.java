package me.vitblokhin.backend.service;

import me.vitblokhin.backend.dto.PageDto;
import me.vitblokhin.backend.dto.UserDto;
import me.vitblokhin.backend.dto.filter.AbstractFilter;
import me.vitblokhin.backend.model.User;

public interface UserService {
    PageDto<UserDto, User> getPage(AbstractFilter filter);

    UserDto findById(Long id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);

    UserDto findByUsername(String username);

    void blockUser(Long id);
    void unblockUser(Long id);
} // interface UserService
