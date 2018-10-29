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
import me.aurora.util.*;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.*;

/**
 * @author 郑杰
 * @date 2018/09/20 14:14:43
 */
@Slf4j
@Service(value = "pictureService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepo pictureRepo;

    @Autowired
    private PictureMapper pictureMapper;

    private final CloseableHttpClient httpclient = HttpClients.createDefault();

    @Override
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
        RestTemplate rest = new RestTemplate();

        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("smfile", resource);

        //设置头部，必须
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param,headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://sm.ms/api/upload", HttpMethod.POST, httpEntity, String.class);

        JSONObject jsonObject = JSONUtil.parseObj(responseEntity.getBody());
        Picture picture = null;
        if(!jsonObject.get(AuroraConstant.Page.CODE).toString().equals(AuroraConstant.SUCCESS)){
           throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,jsonObject.get(AuroraConstant.Page.MSG).toString());
        }
        //转成实体类
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtil.getSize(Integer.valueOf(picture.getSize())));
        picture.setUser(user);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()));
        pictureRepo.save(picture);
        //删除临时文件
        FileUtil.deleteFile(file);
        return picture;
    }

    @Override
    public Picture findById(Long id) {
        if(id == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"id not exist");
        }
        Optional<Picture> picture = pictureRepo.findById(id);
        ValidationUtil.isNull(picture,"id:"+id+"is not find");
        return picture.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
