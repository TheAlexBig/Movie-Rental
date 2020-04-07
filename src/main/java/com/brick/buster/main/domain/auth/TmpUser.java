package com.brick.buster.main.domain.auth;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tmp_user", schema = "public")
@SequenceGenerator(name="tmpuser_sequence", sequenceName="tmpuser_sequence")
public class TmpUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tmpuser_sequence")
    @Column(name = "user_code")
    int code = 0;
    @Column(name = "user_email")
    String email = "";
    @Column(name = "user_username")
    String username = "";
    @Column(name = "user_name")
    String password = "";
    @Column(name = "user_verification_token")
    String verificationToken = "";
}
