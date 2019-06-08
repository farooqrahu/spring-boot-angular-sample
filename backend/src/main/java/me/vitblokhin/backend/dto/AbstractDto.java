package me.vitblokhin.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import me.vitblokhin.backend.enums.Status;
import me.vitblokhin.backend.model.AbstractEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AbstractDto implements Serializable {
    @JsonView({View.All.class, View.Admin.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonView(View.All.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @JsonView(View.All.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    @JsonView({View.All.class, View.Admin.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Status status;

    public AbstractDto() {
    }

    public AbstractDto(AbstractEntity entity) {
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.status = entity.getStatus();
    }
} // class AbstractDto
