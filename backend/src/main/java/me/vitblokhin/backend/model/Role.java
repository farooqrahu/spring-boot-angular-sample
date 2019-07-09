package me.vitblokhin.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

//@Data
//@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


} // class Role
