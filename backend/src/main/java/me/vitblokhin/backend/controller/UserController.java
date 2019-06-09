package me.vitblokhin.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import me.vitblokhin.backend.dto.UserDto;
import me.vitblokhin.backend.dto.viewscope.DetailScope;
import me.vitblokhin.backend.security.jwt.JwtTokenProvider;
import me.vitblokhin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("${rest-api.url}")
public class UserController {
    private final String RESOURCE_URL = "/user";

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @JsonView(DetailScope.Basic.class)
    @PostMapping(RESOURCE_URL)
    public ResponseEntity<UserDto> registration(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @JsonView(DetailScope.Basic.class)
    @GetMapping(RESOURCE_URL)
    public ResponseEntity<UserDto> get(HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);

        return ResponseEntity
                .ok(userService.findByUsername(username));
    }

    @JsonView(DetailScope.Basic.class)
    @PutMapping(RESOURCE_URL)
    public ResponseEntity<UserDto> put(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(token);

        userDto.setId(userService.findByUsername(username).getId());

        userService.update(userDto);

        return ResponseEntity.noContent().build();
    }

} // class UserController
