package com.ptit.management.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User extends Auditable {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "active_flg")
    private Integer activeFlg;
    @Column(name = "role")
    private String role;
    @Column(name = "is_online")
    private Integer isOnline = 0;
    @Column(name = "is_deleted")
    private Integer isDeleted = 0;
    @Column(name = "token_reset")
    private String tokenReset;
    @Column(name = "token_reset_expried", columnDefinition = "DATETIME")
    private Date tokenResetExpried;

    @OneToMany(mappedBy = "user")
    private List<Result> results;
    @OneToMany(mappedBy = "user")
    private List<UserClazz> userClazzes;



}
