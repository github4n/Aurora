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

    private static final String CODE = "code";
    private static final String MSG = "msg";
    private static final String COUNT = "count";
    private static final String DATA = "data";

    public static Map<String,Object> buildPage(List list,Long total) {
        Map<String,Object> map = new HashMap<>(5);
        map.put(PageUtil.CODE,0);
        map.put(PageUtil.MSG,"");
        map.put(PageUtil.COUNT,total);
        map.put(PageUtil.DATA,list);
        return map;
    }
}
