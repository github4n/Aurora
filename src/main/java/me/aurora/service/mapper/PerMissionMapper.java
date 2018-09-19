package me.aurora.service.mapper;

import me.aurora.domain.Menu;
import me.aurora.domain.Permission;
import me.aurora.service.dto.MenuDTO;
import me.aurora.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author 郑杰
 * @date 2018/08/27 13:05:57
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerMissionMapper extends EntityMapper<PermissionDTO, Permission>{
}
