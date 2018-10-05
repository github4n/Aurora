package me.aurora.app.rest.utils;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.User;
import me.aurora.repository.spec.PictureSpec;
import me.aurora.service.PictureService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/09/20 14:13:32
 */
@Slf4j
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    /**
     * 去图床页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/utils/picture/index");
    }

    /**
     * 查询全部图片
     * @param username
     * @param createTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    @Log("查询全部图片")
    @RequiresPermissions(value={"admin", "picture:all","picture:select"}, logical= Logical.OR)
    @GetMapping(value = "/getPictureInfo")
    public Map getInfo(@RequestParam(value = "username",required = false) String username,
                          @RequestParam(value = "createTime",required = false) Timestamp createTime,
                          @RequestParam(value = "endTime",required = false) Timestamp endTime,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return pictureService.getPictureInfo(new PictureSpec(username,createTime,endTime),pageable);
    }

    /**
     * 去上传页面
     * @return
     */
    @RequiresPermissions (value={"admin", "picture:all","picture:upload"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        return new ModelAndView("/utils/picture/add");
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @Log("上传图片")
    @RequiresPermissions (value={"admin", "picture:all","picture:upload"}, logical= Logical.OR)
    @PostMapping(value = "/upload")
    public ResponseEntity upload(@RequestParam MultipartFile file){
        log.warn("REST request to upload Picture : {}" +file.getOriginalFilename());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        pictureService.upload(file,user);
        return ResponseEntity.ok();
    }

    /**
     * 删除图片
     * @param id
     * @return
     */
    @Log("删除图片")
    @RequiresPermissions (value={"admin", "picture:all","picture:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.warn("REST request to delete Picture : {}" +id);
        pictureService.delete(pictureService.findById(id));
        return ResponseEntity.ok();
    }
}
