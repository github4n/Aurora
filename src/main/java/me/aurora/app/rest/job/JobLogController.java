package me.aurora.app.rest.job;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.repository.spec.JobLogSpce;
import me.aurora.service.JobLogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/06 11:40:21
 */
@Slf4j
@RestController
@RequestMapping("jobLog")
public class JobLogController {

    @Autowired
    private JobLogService jobLogService;

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/job/log");
    }

    @Log("查询任务日志")
    @RequiresPermissions(value={"admin", "job:all","job:log"}, logical= Logical.OR)
    @GetMapping(value = "/getJobLogsInfo")
    public Map getJobLogsInfo(@RequestParam(value = "beanName",required = false) String beanName,
                            @RequestParam(value = "methodName",required = false) String methodName,
                            @RequestParam(value = "remark",required = false) String remark,
                            @RequestParam(value = "status",required = false) String status,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return jobLogService.getJobLogsInfo(new JobLogSpce(beanName,methodName,remark,status),pageable);
    }
}
