package com.ptit.test.repository;

import com.ptit.test.entity.Result;
import com.ptit.test.entity.ResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultDetailRepository extends JpaRepository<ResultDetail,String> {
    List<ResultDetail> findResultDetailByResult(Result result);
}
