package com.ptit.management.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {
    private String id;
    private String code;
    private String name;
    private String note;
    private int status;
    private String urlImage;
    private List<Answer> answers;
    private Level level;
    private Exam exam;

    public Question() {
    }

    public Question(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public Question(String id) {
        this.id = id;
    }

    public Question(String name, int status, Exam exam) {
        this.name = name;
        this.status = status;
        this.exam = exam;
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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name",length = 1000)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "url_image")
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return status == question.status && Objects.equals(id, question.id) && Objects.equals(code, question.code) && Objects.equals(name, question.name) && Objects.equals(note, question.note) && Objects.equals(urlImage, question.urlImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, note, status, urlImage);
    }

    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @ManyToOne
    @JoinColumn(name = "Levelid", referencedColumnName = "id", nullable = true)
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @ManyToOne
    @JoinColumn(name = "Examid", referencedColumnName = "id", nullable = false)
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
