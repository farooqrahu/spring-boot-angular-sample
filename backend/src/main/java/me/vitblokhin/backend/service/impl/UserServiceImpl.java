package me.vitblokhin.backend.service.impl;

import me.vitblokhin.backend.dto.UserDto;
import me.vitblokhin.backend.dto.filter.AbstractFilter;
import me.vitblokhin.backend.enums.Status;
import me.vitblokhin.backend.exception.ItemAlreadyExistsException;
import me.vitblokhin.backend.exception.ItemNotFoundException;
import me.vitblokhin.backend.model.Role;
import me.vitblokhin.backend.model.User;
import me.vitblokhin.backend.repository.RoleRepository;
import me.vitblokhin.backend.repository.UserRepository;
import me.vitblokhin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getPage(AbstractFilter filter) {

        List<UserDto> userList = userRepository
                .findAll(PageRequest.of(filter.getPage(), filter.getSize()))
                .getContent().stream().map(UserDto::new).collect(Collectors.toList());

        return userList;
    }

    @Override
    public UserDto findById(Long id) {
        return new UserDto(this.get(id));
    }

    @Override
    public UserDto create(UserDto userDto) {
        if (this.userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ItemAlreadyExistsException("User with username: " + userDto.getUsername() + " is already exist");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ItemNotFoundException("Default role for user not found"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return new UserDto(userRepository.save(user));
    }

    @Override
    public UserDto update(UserDto user) {
        User oldUser = this.get(user.getId());

        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());

        oldUser.setUpdatedAt(LocalDateTime.now());

        return new UserDto(userRepository.save(oldUser));
    }

    @Override
    public void delete(Long id) {
        this.get(id);
        userRepository.deleteById(id);
    }

    private User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
    }
} // class UserServiceImpl
