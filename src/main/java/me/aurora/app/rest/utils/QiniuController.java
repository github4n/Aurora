package me.aurora.app.rest.utils;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.utils.QiniuConfig;
import me.aurora.repository.spec.QiNiuContentSpec;
import me.aurora.service.QiNiuService;
import me.aurora.util.HttpContextUtils;
import me.aurora.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/02
 */
@Slf4j
@RestController
@RequestMapping("qiNiu")
public class QiniuController {

    @Autowired
    private QiNiuService qiNiuService;

    @GetMapping(value = "/index")
    public ModelAndView index(){
        QiniuConfig qiniuConfig = qiNiuService.findById(1L);
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("qiniuConfig",qiniuConfig);
        if(qiniuConfig == null){
            request.setAttribute("qiniuConfig",new QiniuConfig());
        }
        return new ModelAndView("/utils/qiNiu/index");
    }

    /**
     * 查询全部文件
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @Log("查询七牛云全部文件")
    @GetMapping(value = "/getContentInfo")
    public Map getContentInfo(@RequestParam(value = "name",required = false) String name,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        QiniuConfig qiniuConfig = qiNiuService.findById(1L);
        if(qiniuConfig == null){
            return PageUtil.buildPage(null,0L);
        }
        return qiNiuService.getContentInfo(new QiNiuContentSpec(name,qiniuConfig.getBucket()),pageable);
    }

    @Log("配置七牛对象存储")
    @PostMapping(value = "/config")
    public ResponseEntity qiNiuConfig(@RequestBody @Validated(QiniuConfig.Update.class) QiniuConfig qiniuConfig){
        log.warn("REST request to qiNiuConfig QiniuConfig : {}" +qiniuConfig);
        qiniuConfig.setId(1L);
        qiNiuService.updateConfig(qiniuConfig);
        return ResponseEntity.ok();
    }

    /**
     * 去上传页面
     * @return
     */
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        return new ModelAndView("/utils/qiNiu/add");
    }

    /**
     * 上传文件到七牛云
     * @param file
     * @return
     */
    @Log("上传文件到七牛云")
    @PostMapping(value = "/upload")
    public ResponseEntity upload(@RequestParam MultipartFile file){
        log.warn("REST request to upload qiNiu : {}" +file.getOriginalFilename());
        qiNiuService.upload(file,qiNiuService.findById(1L));
        return ResponseEntity.ok();
    }
    /**
     * 同步七牛云数据到数据库
     * @return
     */
    @Log("同步七牛云数据到数据库")
    @PostMapping(value = "/synchronize")
    public ResponseEntity synchronize(){
        log.warn("REST request to synchronize qiNiu : {}");
        qiNiuService.synchronize(qiNiuService.findById(1L));
        return ResponseEntity.ok();
    }

    /**
     * 下载七牛云文件
     * @param id
     * @return
     * @throws Exception
     */
    @Log("下载七牛云文件")
    @PostMapping(value = "/download")
    public ResponseEntity download(@RequestParam Long id) throws UnsupportedEncodingException {
        log.warn("REST request to download qiNiu : {}" +id);
        return qiNiuService.download(qiNiuService.findByContentId(id),qiNiuService.findById(1L));
    }

    /**
     * 删除七牛云文件
     * @param id
     * @return
     * @throws Exception
     */
    @Log("删除七牛云文件")
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id) throws UnsupportedEncodingException {
        log.warn("REST request to delete qiNiu : {}" +id);
        qiNiuService.delete(qiNiuService.findByContentId(id),qiNiuService.findById(1L));
        return ResponseEntity.ok();
    }
}
