package com.brick.buster.main.domain.auth;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="privilege", schema = "public")
@SequenceGenerator(name="privilege_sequence", sequenceName="privilege_sequence")
public class Privilege implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilege_sequence")
    @Column(name = "privilege_code")
    private int code  = 0;

    @Column(name = "privilege_name")
    private String name = "NON_ROLE";

    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles = null;

    public Privilege() {
    }

    public Privilege(String name, List<Role> roles) {
        this.name = name;
        this.roles = roles;
    }

    public Privilege(String name) {
        this.name = name;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
