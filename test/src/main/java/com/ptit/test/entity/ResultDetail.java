package com.ptit.test.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Table
@Entity(name = "result_detail")
public class ResultDetail {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultId", referencedColumnName = "id", nullable = false)
    private Result result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answerId", referencedColumnName = "id", nullable = false)
    private Answer answer;

    private Boolean isPick;

    private Integer ordinal;
}
