package me.aurora.service;

import me.aurora.domain.utils.WebCount;
import org.springframework.scheduling.annotation.Async;

/**
 * @author 郑杰
 * @date 2018/10/27 13:14:50
 */
public interface WebCountService {

    /**
     * 保存记录
     */
    @Async
    void save();

    /**
     * 得到近7日的pv
     * @return
     */
    Long getWeekPv();

    /**
     * 得到今日访问量
     * @param toString
     * @return
     */
    WebCount findByDate(String toString);
}
