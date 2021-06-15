package com.ptit.test.repository;

import com.ptit.test.entity.Result;
import com.ptit.test.entity.ResultDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultDetailRepository extends JpaRepository<ResultDetail,String> {
    @Query(value = "select * from result_detail where  result_id = ?1 ",nativeQuery = true)
    List<ResultDetail> findResultDeta(String resultId);
}
