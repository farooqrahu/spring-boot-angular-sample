package me.vitblokhin.backend.controller;

import me.vitblokhin.backend.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest-api.url}")
public class MainController {

    @GetMapping()
    public ResponseEntity<MessageDto> get() {
        return ResponseEntity.ok(new MessageDto("Hello world"));
    }
} // class MainController
