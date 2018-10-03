package me.aurora.util;

/**
 * 常用静态常量
 * @author 郑杰
 * @date 2018/09/29 12:52:06
 */
public class AuroraConstant {

    public static final String SUCCESS = "success";

    /**
     * 用于排序
     */
    public static class Soft{

        public static final String DESC = "desc";

        public static final String ASC = "asc";
    }

    /**
     * 用于分页
     */
    public static class Page{

        public static final String CODE = "code";

        public static final String MSG = "msg";

        public static final String COUNT = "count";

        public static final String DATA = "data";
    }

    /**
     * 用于七牛云zone与机房对应关系
     */
    public static class QiNiu{

        public static final String HUAD = "华东";

        public static final String HUAB = "华北";

        public static final String HUAN = "华南";

        public static final String BEIM = "北美";

        public static final String DNY = "东南亚";
    }

    /**
     * 用于IP定位转换
     */
    public static final String REGION = "内网IP|内网IP";

    /**
     * 常用接口
     */
    public static class Url{
        public static final String SM_MS_URL = "https://sm.ms/api/upload";
    }
}
