package me.vitblokhin.backend.service;

import me.vitblokhin.backend.dto.UserDto;
import me.vitblokhin.backend.dto.filter.AbstractFilter;

import java.util.List;

public interface UserService {
    List<UserDto> getPage(AbstractFilter filter);

    UserDto findById(Long id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);
} // interface UserService
