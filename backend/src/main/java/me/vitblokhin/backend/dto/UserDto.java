package me.vitblokhin.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.vitblokhin.backend.model.User;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto  extends AbstractDto implements Serializable {
    @NotEmpty
    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Set<RoleDto> roles;

    public UserDto() {
        super();
    }

    public UserDto(User user) {
        super(user);
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();

        this.roles = user.getRoles()
                .stream()
                .map(RoleDto::new)
                .collect(Collectors.toSet());
    }
} // class UserDto
