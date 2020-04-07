package com.brick.buster.main.domain.auth;

import com.brick.buster.main.domain.business.Movie;
import com.brick.buster.main.domain.business.Purchase;
import com.brick.buster.main.util.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="like", schema = "public")
@SequenceGenerator(name="like_sequence", sequenceName="like_sequence")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_sequence")
    @Column(name = "like_code")
    private int code = 0;
    @Column(name = "like_object")
    private String object = "none";
    @Column(name="like_reference")
    private String reference = "none";

    @JsonBackReference(value = "user-like")
    @ManyToOne
    @JoinColumn(name="user_code", nullable=false)
    private User user;

    public Like() {
    }

    public Like(String object, String reference, User user) {
        this.object = object;
        this.reference = reference;
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
