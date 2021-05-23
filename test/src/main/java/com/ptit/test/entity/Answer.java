package com.ptit.test.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Answer {
    private String id;
    private String code;
    private String name;
    private Integer status;
    private Integer isTrue;
    private String urlImage;
    private String note;
    private Question question;
    
   
    private List<ResultDetail> resultDetails;

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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "is_true")
    public Integer getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Integer isTrue) {
        this.isTrue = isTrue;
    }

    @Basic
    @Column(name = "url_image")
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return status == answer.status && isTrue == answer.isTrue && Objects.equals(id, answer.id) && Objects.equals(code, answer.code) && Objects.equals(name, answer.name) && Objects.equals(urlImage, answer.urlImage) && Objects.equals(note, answer.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, status, isTrue, urlImage, note);
    }

    @ManyToOne
    @JoinColumn(name = "Questionid", referencedColumnName = "id", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
    @OneToMany(mappedBy = "answer")
    public List<ResultDetail> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetail> results) {
        this.resultDetails = results;
    }
}
