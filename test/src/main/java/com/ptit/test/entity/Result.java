package com.ptit.test.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class Result {
    private String examId;
    private Instant createAt;
    private Instant deadline;
    private String id;
    private User user;
    private List<ResultDetail> resultDetails;

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "Examid")
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examid) {
        this.examId = examid;
    }

    @Basic
    @Column(name = "create_at")
    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(examId, result.examId) && Objects.equals(createAt, result.createAt) && Objects.equals(id, result.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, createAt, id);
    }

    @ManyToOne
    @JoinColumn(name = "Userid2", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @OneToMany(mappedBy = "result")
    public List<ResultDetail> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetail> resultDetails) {
        this.resultDetails = resultDetails;
    }
}
