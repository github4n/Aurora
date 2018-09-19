package me.aurora.service.mapper;

import me.aurora.domain.Role;
import me.aurora.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author 郑杰
 * @date 2018/08/27 12:34:22
 */
@Mapper(componentModel = "spring",uses = {PerMissionMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, Role>{

}
