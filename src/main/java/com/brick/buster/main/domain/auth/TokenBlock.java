package com.brick.buster.main.domain.auth;


import antlr.Token;

import javax.persistence.*;

@Entity
@Table(name="token", schema = "public")
@SequenceGenerator(name="token_sequence", sequenceName="token_sequence")
public class TokenBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_sequence")
    @Column(name = "token_code")
    private int code = 0;
    @Column(name = "token_blocked", length = 1024)
    private String blocked = "";

    public TokenBlock(String blocked) {
        this.blocked = blocked;
    }

    TokenBlock(){}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }
}
