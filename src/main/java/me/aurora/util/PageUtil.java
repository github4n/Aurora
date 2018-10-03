package me.aurora.util;

import org.springframework.data.domain.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具类
 * @author 郑杰
 * @date 2018/08/25 15:00:23
 */
public class PageUtil {

    public static Map<String,Object> buildPage(List list,Long total) {
        Map<String,Object> map = new HashMap<>(5);
        map.put(AuroraConstant.Page.CODE,0);
        map.put(AuroraConstant.Page.MSG,"");
        map.put(AuroraConstant.Page.COUNT,total);
        map.put(AuroraConstant.Page.DATA,list);
        return map;
    }
}
