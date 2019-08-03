package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created on 2019/8/2.
 *
 * @author yangsen
 */
@Repository
public interface ResultDao extends JpaRepository<com.example.demo.ResultDO, Long> {

    public static void main(String[] args) {
        System.out.println(new BigDecimal(Math.random() * 10));
    }

}
