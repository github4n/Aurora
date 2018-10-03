package me.aurora.util;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 七牛云存储工具类
 * @author 郑杰
 * @date 2018/10/02 12:37:37
 */
public class QiNiuUtil {

    /**
     * 得到机房的对应关系
     * @param zone
     * @return
     */
    public static Configuration getConfiguration(String zone){

        if(AuroraConstant.QiNiu.HUAD.equals(zone)){
            return new Configuration(Zone.zone0());
        } else if(AuroraConstant.QiNiu.HUAB.equals(zone)){
            return new Configuration(Zone.zone1());
        } else if(AuroraConstant.QiNiu.HUAN.equals(zone)){
            return new Configuration(Zone.zone2());
        } else if (AuroraConstant.QiNiu.BEIM.equals(zone)){
            return new Configuration(Zone.zoneNa0());
        } else {
            return new Configuration(Zone.zoneAs0());
        }
    }

    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     * @param file
     * @return
     */
    public static String getKey(String file){
        StringBuffer key = new StringBuffer(FileUtil.getFileNameNoEx(file));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        key.append(sdf.format(date));
        key.append(".");
        key.append(FileUtil.getExtensionName(file));
        return key.toString();
    }
}
