package com.ptit.test.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "User_Clazz", schema = "lms_android", catalog = "")
public class UserClazz extends Auditable{
    private String id;
    private User user;
    private Clazz clazz;
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "Userid", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userBy) {
        this.user = userBy;
    }

    @ManyToOne
    @JoinColumn(name = "Clazzid", referencedColumnName = "id", nullable = false)
    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
