package me.vitblokhin.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import me.vitblokhin.backend.dto.PageDto;
import me.vitblokhin.backend.dto.UserDto;
import me.vitblokhin.backend.dto.viewscope.DetailScope;
import me.vitblokhin.backend.dto.filter.AbstractFilter;
import me.vitblokhin.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("${rest-api.admin-url}")
public class AdminUserController {
    private final String RESOURCE_URL = "/users";

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(DetailScope.Basic.class)
    @PostMapping(RESOURCE_URL)
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @JsonView(DetailScope.Admin.class)
    @GetMapping(RESOURCE_URL + "/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable("id") Long id) {

        return ResponseEntity
                .ok(userService.findById(id));
    }

    @JsonView(DetailScope.Admin.class)
    @GetMapping(RESOURCE_URL)
    public ResponseEntity<PageDto> getPage(AbstractFilter filter) {

        return ResponseEntity
                .ok(userService.getPage(filter));
    }

    @GetMapping(RESOURCE_URL + "/{id}/block")
    public ResponseEntity<UserDto> block(@PathVariable("id") Long id) {

        userService.blockUser(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping(RESOURCE_URL + "/{id}/unblock")
    public ResponseEntity<UserDto> unblock(@PathVariable("id") Long id) {

        userService.unblockUser(id);

        return ResponseEntity.ok().build();
    }
} // class AdminUserController
