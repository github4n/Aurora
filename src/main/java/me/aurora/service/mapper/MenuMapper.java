package me.aurora.service.mapper;

import me.aurora.domain.Menu;
import me.aurora.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author 郑杰
 * @date 2018/08/27 12:23:20
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu>{

    /**
     * 自定义转换
     * @param menu
     * @param rolesSelect
     * @return
     */
    @Mapping(source = "rolesSelect",target = "rolesSelect")
    MenuDTO toDto(Menu menu,String rolesSelect);

}
