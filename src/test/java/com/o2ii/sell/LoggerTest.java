package com.o2ii.sell;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test() {
        // slf4j是日志的门面，logback是具体的日志框架
        log.info("hello=======");
    }
}
