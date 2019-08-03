package com.example.demo.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created on 2019/8/2.
 *
 * @author yangsen
 */
@Repository
public interface TestDao extends JpaRepository<com.example.demo.TestDO, Long> {

    public static void main(String[] args) {
        System.out.println(new BigDecimal(Math.random() * 10));
    }

}
