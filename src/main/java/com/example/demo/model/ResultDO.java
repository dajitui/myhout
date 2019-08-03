package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created on 2019/8/2.
 *
 * @author yangsen
 */
@Entity
@Data
@Table(name = "result")
public class ResultDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "uid")
    int uid;

    @Column(name = "articleid")
    long articleid;

    @Column(name = "value")
    BigDecimal value;

}
