package me.aurora.domain.vo;

import lombok.Data;

/**
 * 菜单路由
 * @author 郑杰
 * @date 2018/08/22 12:20:27
 */
@Data
public class MenuVo {

    private String name;

    private String path;

    private String component;

    private String iframe;
}

