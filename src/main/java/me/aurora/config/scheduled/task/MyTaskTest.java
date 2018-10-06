package me.aurora.config.scheduled.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 郑杰
 * @date 2018/10/06 10:03:43
 */
@Slf4j
@Component
public class MyTaskTest {

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }

    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }
}
