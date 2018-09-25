package me.aurora.config.baidu.ueditor.upload;

import me.aurora.config.baidu.ueditor.PathFormat;
import me.aurora.config.baidu.ueditor.define.AppInfo;
import me.aurora.config.baidu.ueditor.define.BaseState;
import me.aurora.config.baidu.ueditor.define.State;
import me.aurora.domain.Picture;
import me.aurora.domain.User;
import me.aurora.service.PictureService;
import me.aurora.util.FileUtil;
import me.aurora.util.SpringContextUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author baidu.com
 */
public class Uploader {

    private PictureService pictureService = SpringContextUtils.getBean("pictureService",PictureService.class);

	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	private final String TYPE = "isBase64";
	private final String STATE = "true";

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public Uploader(){}

    /**
     * 自定义图片上传到sm.ms，不占用本地空间
     * @return
     */
	public final State doExec() {

		String filedName = (String) this.conf.get("fieldName");

// 上传到本地使用该方法
//		return BinaryUploader.save(this.request, this.conf);
        Uploader uploader = new Uploader();
        return uploader.save(this.request,filedName);
	}

    /**
     * 上传到sm.ms图床
     * @param request
     * @return
     */
    public State save(HttpServletRequest request,String filedName){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile(filedName);
        if(file==null){
            return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Picture picture = pictureService.upload(file,user);
        State state = new BaseState(true);
        state.putInfo("url", picture.getUrl());
        state.putInfo("type", FileUtil.getExtensionName(file.getOriginalFilename()));
        state.putInfo("original", file.getOriginalFilename());
        state.putInfo( "size", file.getSize());
        state.putInfo( "title", file.getOriginalFilename());
        return state;
    }
}
