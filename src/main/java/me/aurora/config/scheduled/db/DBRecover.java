package me.aurora.config.scheduled.db;

import lombok.extern.slf4j.Slf4j;
import me.aurora.config.AuroraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author 郑杰
 * @date 2018/09/18 11:28:39
 * 还原数据库
 */
@Slf4j
@Component
public class DBRecover {

    @Autowired
    private AuroraProperties auroraProperties;

    /**
     * 还原数据库
     * @Scheduled(cron = "0 0 1 * * ?")
     * @Scheduled(cron = "0/5 * * * * ?")
     */
    public void recover(){
        log.warn("=====>>>>>定时恢复数据库");
        String path = "";
        String userName = auroraProperties.getDb().getUsername();
        String password = auroraProperties.getDb().getPassword();
        String database = auroraProperties.getDb().getDbName();
        try {
            InputStream file = new ClassPathResource(auroraProperties.getDb().getPath()).getInputStream();
            path = auroraProperties.getDb().getLinuxDbPath()+auroraProperties.getDb().getPathName();
            Path dBPaht = Paths.get(path);
            /**
             * 将本地的sql复制到linux
             */
            Files.copy(file, dBPaht, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] cmd=new String[]{"/bin/sh ","-c ","/usr/bin/mysqldump -u"+userName+"  -p"+password+" "+database+ " < " + path};
        try {
            Process p =  Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            // 0 表示线程正常终止。
            if (p.waitFor() == 0) {
                System.out.println("数据库恢复成功！");
            } else {
                System.out.println("数据库恢复失败！");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
