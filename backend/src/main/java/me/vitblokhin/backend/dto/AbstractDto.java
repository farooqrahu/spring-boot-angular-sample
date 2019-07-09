package me.vitblokhin.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import me.vitblokhin.backend.dto.viewscope.DetailScope;
import me.vitblokhin.backend.enums.Status;
import me.vitblokhin.backend.model.AbstractEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AbstractDto implements Serializable {
    @JsonView(DetailScope.Admin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonView(DetailScope.Full.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @JsonView(DetailScope.Full.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updatedAt;

    @JsonView(DetailScope.Admin.class)
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
} // class AbstractDto
