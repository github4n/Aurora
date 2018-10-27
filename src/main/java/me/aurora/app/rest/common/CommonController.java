package me.aurora.app.rest.common;

import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.service.SysLogService;
import me.aurora.service.WebCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共的controller
 * @author 郑杰
 * @date 2018/09/25 19:08:41
 */
@RestController
public class CommonController {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private WebCountService webCountService;

    @Log("访问swagger")
    @GetMapping(value = "/swagger/index")
    public ModelAndView index(){
        return new ModelAndView("/system/api/index");
    }

    /**
     * 访问量计数
     * @return
     */
    @GetMapping("/pageviews/plusCount")
    public ResponseEntity plusCount(){
        webCountService.save();
        return ResponseEntity.ok();
    }

    /**
     * 访问量查询
     * @return
     */
    @GetMapping("/pageviews/get")
    public Map getCount(){
        Map map = new HashMap();
        map.put("pv",webCountService.findByDate(LocalDate.now().toString()).getCounts());
        map.put("weekPv",webCountService.getWeekPv());
        map.put("ip",sysLogService.getIp());
        map.put("weekIp",sysLogService.getWeekIP());
        return map;
    }
}
