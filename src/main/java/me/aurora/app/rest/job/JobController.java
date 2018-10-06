package me.aurora.app.rest.job;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.job.Job;
import me.aurora.repository.spec.JobSpce;
import me.aurora.service.JobService;
import me.aurora.util.HttpContextUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/05 19:12:24
 */
@Slf4j
@RestController
@RequestMapping("job")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/job/index");
    }

    @Log("查询所有任务")
    @RequiresPermissions(value={"admin", "job:all","job:select"}, logical= Logical.OR)
    @GetMapping(value = "/getJobsInfo")
    public Map getJobsInfo(@RequestParam(value = "beanName",required = false) String beanName,
                            @RequestParam(value = "methodName",required = false) String methodName,
                            @RequestParam(value = "remark",required = false) String remark,
                            @RequestParam(value = "status",required = false) String status,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return jobService.getJobsInfo(new JobSpce(beanName,methodName,remark,status),pageable);
    }

    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "job:all","job:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        return new ModelAndView("/job/add");
    }

    /**
     * 新增
     * @param job
     * @return
     */
    @Log("新增任务")
    @RequiresPermissions (value={"admin", "job:all","job:add"}, logical= Logical.OR)
    @PostMapping(value = "/insert")
    public ResponseEntity insert(@Validated(Job.New.class) @RequestBody Job job){
        log.warn("REST request to insert Job : {}"+job);
        jobService.insert(job);
        return ResponseEntity.ok();
    }

    /**
     * 更新状态
     * @param id
     * @return
     */
    @Log("更新任务状态")
    @RequiresPermissions (value={"admin", "job:all","job:status"}, logical= Logical.OR)
    @PutMapping(value = "/updateStatus")
    public ResponseEntity updateStatus(@RequestParam Long id){
        log.warn("REST request to updateStatus Job : {}" +id);
        jobService.updateStatus(jobService.findById(id));
        return ResponseEntity.ok();
    }


    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "job:all","job:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        Job job = jobService.findById(id);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("job", job);
        return new ModelAndView("/job/update");
    }

    /**
     * 更新任务
     * @param job
     * @return
     */
    @Log("更新任务")
    @RequiresPermissions (value={"admin", "job:all","job:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@Validated(Job.Update.class) @RequestBody Job job) {
        log.warn("REST request to update Job : {}" +job);
        jobService.update(jobService.findById(job.getId()),job);
        return ResponseEntity.ok();
    }

    /**
     * 删除任务
     * @param id
     * @return
     */
    @Log("删除任务")
    @RequiresPermissions (value={"admin", "job:all","job:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.warn("REST request to delete Job : {}" +id);
        jobService.delete(jobService.findById(id));
        return ResponseEntity.ok();
    }

}
