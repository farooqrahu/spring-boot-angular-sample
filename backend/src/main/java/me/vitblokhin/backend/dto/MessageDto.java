package me.vitblokhin.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime time;

    public MessageDto(String text) {
        this.text = text;
        this.time = LocalDateTime.now();
    }
} // class MessageDto
