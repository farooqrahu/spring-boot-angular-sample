package me.vitblokhin.backend.config;

import me.vitblokhin.backend.enums.Status;
import me.vitblokhin.backend.model.Role;
import me.vitblokhin.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Boolean isSet = false;

    private final RoleRepository roleRepository;

    @Autowired
    public InitialDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (this.isSet) {
            return;
        }
        this.createRoleIfNotFound("ROLE_ADMIN");
        this.createRoleIfNotFound("ROLE_USER");

        this.isSet = true;
    }

    private void createRoleIfNotFound(String roleName) {
        Optional optRole = this.roleRepository.findByName(roleName);
        if (!optRole.isPresent()) {
            Role role = new Role();
            role.setName(roleName);
            role.setCreatedAt(LocalDateTime.now());
            role.setStatus(Status.ACTIVE);
            this.roleRepository.save(role);
        }
    }
} // class InitialDataLoader
