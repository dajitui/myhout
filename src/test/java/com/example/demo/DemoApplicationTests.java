package com.example.demo;

import com.example.demo.TestDO;
import com.example.demo.mapper.TestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Resource
    TestDao testDao;

    @Test
    public void contextLoads() {
        int i = 0;
        List<TestDO> testDOList = new LinkedList<>();
        while (true) {
            TestDO testDO = new TestDO();
            testDO.setIid(new BigDecimal(new BigDecimal(Math.random() * 10000 + Math.random() * 1000).intValue()).intValue());
            testDO.setScore(new BigDecimal(Math.random() * 10));
            testDO.setTs(new Date());
            if (i % 5 == 0) {
                testDO.setUid(i);
            } else {
                testDO.setUid(i / 5 * 5);
            }
            i++;
            testDOList.add(testDO);
            if (testDOList.size() >= 1000) {
                testDao.saveAll(testDOList);
                testDOList = new LinkedList<>();
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal(Math.random() * 100 + Math.random() * 10).intValue());
    }

}
