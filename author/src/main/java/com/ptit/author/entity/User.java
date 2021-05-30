package com.ptit.author.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
public class User  {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    private String phoneNumber;
    @Column(name = "email")
    private String email;
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
    @Column(name = "url_image")
    private String urlImage;


}
