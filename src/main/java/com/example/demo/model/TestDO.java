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
@Table(name = "test")
public class TestDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "uid")
    int uid;

    @Column(name = "iid")
    int iid;

    @Column(name = "score")
    BigDecimal score;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "ts")
    Date ts;

}
