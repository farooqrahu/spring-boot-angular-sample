package me.vitblokhin.backend.service.impl;

import me.vitblokhin.backend.dto.PageDto;
import me.vitblokhin.backend.dto.UserDto;
import me.vitblokhin.backend.dto.filter.AbstractFilter;
import me.vitblokhin.backend.enums.Status;
import me.vitblokhin.backend.exception.ItemAlreadyExistsException;
import me.vitblokhin.backend.exception.ItemNotFoundException;
import me.vitblokhin.backend.exception.ServerException;
import me.vitblokhin.backend.exception.StatusChangeException;
import me.vitblokhin.backend.model.Role;
import me.vitblokhin.backend.model.User;
import me.vitblokhin.backend.repository.RoleRepository;
import me.vitblokhin.backend.repository.UserRepository;
import me.vitblokhin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Transactional
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
    public PageDto<UserDto, User> getPage(AbstractFilter filter) {

        PageDto<UserDto, User> page = new PageDto<>(userRepository
                .findAll(PageRequest.of(filter.getPage(), filter.getSize())), UserDto::new);

        return page;
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
                .orElseThrow(() -> new ServerException("User creation fail: default role for user not found"));
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

    @Override
    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::new)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
    }

    @Override
    public void blockUser(Long id) {
        User user = this.get(id);
        if(user.getStatus().equals(Status.ACTIVE)){
            user.setStatus(Status.NOT_ACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        } else {
            throw new StatusChangeException("User is already blocked, or user is deleted");
        }
    }

    @Override
    public void unblockUser(Long id) {
        User user = this.get(id);
        if(user.getStatus().equals(Status.NOT_ACTIVE)){
            user.setStatus(Status.ACTIVE);
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        } else {
            throw new StatusChangeException("User is already unblocked, or user is deleted");
        }
    }

    private User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User not found"));
    }
} // class UserServiceImpl
