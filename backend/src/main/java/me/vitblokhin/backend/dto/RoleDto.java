package me.vitblokhin.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vitblokhin.backend.model.Role;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends AbstractDto implements Serializable {
    private String name;

    public RoleDto() {
        super();
    }

    public RoleDto(Role role) {
        super(role);
        this.name = role.getName();
    }
} // class RoleDto
