package com.ptit.management.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Clazz extends Auditable{
    public  Clazz(String id){
        this.id=id;
    }
    private String id;
    private String name;
    private String code;
    private String description;
    private Integer status;
    private List<Exam> exams;
    private List<UserClazz> userClazzes;
    private String idTeacher;
    public Clazz() {

    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

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

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return Objects.equals(id, clazz.id) && Objects.equals(name, clazz.name) && Objects.equals(code, clazz.code) && Objects.equals(description, clazz.description) && Objects.equals(status, clazz.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, description, status);
    }

    @OneToMany(mappedBy = "clazz")
    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    @OneToMany(mappedBy = "clazz")
    public List<UserClazz> getUserClazzes() {
        return userClazzes;
    }

    public void setUserClazzes(List<UserClazz> userClazzes) {
        this.userClazzes = userClazzes;
    }
}
