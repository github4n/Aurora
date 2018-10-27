package me.aurora.service.impl;

import me.aurora.domain.utils.WebCount;
import me.aurora.repository.WebCountRepo;
import me.aurora.service.WebCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/10/27 13:14:50
 */
@Service
public class WebCountServiceImpl implements WebCountService {

    @Autowired
    private WebCountRepo webCountRepo;

    @Override
    public void save() {

        LocalDate localDate = LocalDate.now();
        WebCount webCount = webCountRepo.findByDate(localDate.toString());

        if(webCount != null){
            webCount.setCounts(webCount.getCounts()+1);
        }else {
            webCount = new WebCount();
            webCount.setCounts(1L);
            webCount.setDate(localDate.toString());
        }
        webCountRepo.save(webCount);
    }

    @Override
    public Long getWeekPv() {

        LocalDate localDate = LocalDate.now();
        List<WebCount> webCounts = webCountRepo.findPv(localDate.minusDays(7).toString(),localDate.plusDays(1).toString());
        Long count = 0L;
        for (WebCount webCount : webCounts) {
            count += webCount.getCounts();
        }
        return count;
    }

    @Override
    public WebCount findByDate(String toString) {
        return webCountRepo.findByDate(toString);
    }
}
