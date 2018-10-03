package me.aurora.util;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.File;
import java.lang.reflect.Method;

/**
 * @author 郑杰
 * @date 2018/09/18 9:48:00
 */
public class AddressUtils {

    private AddressUtils() {
    }

    private static Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static String getCityInfo(String ip) {
        try {
            Resource resource = new ClassPathResource("ip2region/ip2region.db");
            File file = resource.getFile();
            if (!file.exists()) {
                log.error("Error: Invalid ip2region.db file");
            }
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.error("Error: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String address = dataBlock.getRegion().replace("0|","");
            /**
             * 太难看了，转换下
             */
            return address.equals(AuroraConstant.REGION)?"内网IP":address;
        } catch (Exception e) {
            log.error("获取地址信息异常：{}", e);
        }
        return "";
    }

}