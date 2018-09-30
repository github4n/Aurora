package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.Picture;
import me.aurora.domain.User;
import me.aurora.repository.PictureRepo;
import me.aurora.repository.spec.PictureSpec;
import me.aurora.service.PictureService;
import me.aurora.service.dto.PictureDto;
import me.aurora.service.mapper.PictureMapper;
import me.aurora.util.FileUtil;
import me.aurora.util.PageUtil;
import me.aurora.util.SizeUtil;
import me.aurora.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/09/20 14:14:43
 */
@Slf4j
@Service(value = "pictureService")
public class PictureServiceImpl implements PictureService {

    private final String uploadUrl = "https://sm.ms/api/upload";

    private final String SUCCESS = "success";

    private final String CODE = "code";

    private final String MSG = "msg";

    @Autowired
    private PictureRepo pictureRepo;

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    @Transactional(readOnly = true)
    public Map getPictureInfo(PictureSpec pictureSpec, Pageable pageable) {
        Page<Picture> picturePage = pictureRepo.findAll(pictureSpec,pageable);
        Page<PictureDto> pictureDtos = picturePage.map(pictureMapper::toDto);
        return PageUtil.buildPage(pictureDtos.getContent(),picturePage.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, User user) {
        File file = FileUtil.toFile(multipartFile);
        //将参数合成一个请求
        HttpEntity requestEntity = new HttpEntity<>(file);
        RestTemplate rest = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        //sm.ms 固定格式
        param.add("smfile", resource);
        //执行HTTP请求
        String str = rest.postForObject(uploadUrl, param, String.class);
        JSONObject jsonObject = JSONUtil.parseObj(str);
        Picture picture = null;
        if(!jsonObject.get(CODE).toString().equals(SUCCESS)){
           throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,jsonObject.get(MSG).toString());
        }
        //转成实体类
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(SizeUtil.getSize(Integer.valueOf(picture.getSize())));
        picture.setUser(user);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()));
        pictureRepo.save(picture);
        //删除临时文件
        FileUtil.deleteFile(file);
        return picture;
    }

    @Override
    @Transactional(readOnly = true)
    public Picture findById(Long id) {
        Picture picture = pictureRepo.findById(id).get();
        ValidationUtil.isNull(picture,"id:"+id+"is not find");
        return picture;
    }

    @Override
    public void delete(Picture picture) {
        RestTemplate rest = new RestTemplate();
        try {
            ResponseEntity<String> str = rest.getForEntity(picture.getDelete(), String.class);
            if(str.getStatusCode().is2xxSuccessful()){
                pictureRepo.delete(picture);
            }
        //如果删除的地址出错，直接删除数据库数据
        } catch(Exception e){
            pictureRepo.delete(picture);
        }

    }
}
