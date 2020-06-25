package com.brick.buster.main.domain.auth;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="role", schema = "public")
@SequenceGenerator(name="role_sequence", sequenceName="role_sequence")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @Column(name = "role_code")
    private int code = 0;

    @Column(name = "role_name")
    private String name  = "ROLE_USER";

    @ManyToMany(mappedBy = "roles")
    private List<User> users = null;

    @ManyToMany
    @JoinTable(
            name = "role_privilege",
            joinColumns =  @JoinColumn(name = "role_id", referencedColumnName = "role_code"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_code"))
    private List<Privilege> privileges = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Role() {
    }

    public Role(String name, List<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }
}
