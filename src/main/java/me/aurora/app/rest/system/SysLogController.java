package me.aurora.app.rest.system;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.repository.spec.LogSpec;
import org.slf4j.Logger;
import me.aurora.service.SysLogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/08/23 9:57:14
 */
@Slf4j
@RestController
@RequestMapping("sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 去日志页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        log.warn("REST request to toLogPage");
        return new ModelAndView("/system/log/index");
    }

    /**
     * 查询所有日志
     * @param username
     * @param method
     * @param operation
     * @param location
     * @param page
     * @param limit
     * @return
     */
    @RequiresPermissions(value={"admin", "log:all","log:select"}, logical= Logical.OR)
    @GetMapping(value = "/getLogInfo")
    public Map getLogInfo(@RequestParam(value = "username",required = false) String username,
                          @RequestParam(value = "method",required = false) String method,
                          @RequestParam(value = "operation",required = false) String operation,
                          @RequestParam(value = "location",required = false) String location,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "limit",defaultValue = "10")Integer limit){

        log.warn("REST request to findAll Log");
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return sysLogService.getLogInfo(new LogSpec(username,method,operation,location),pageable);
    }
}
