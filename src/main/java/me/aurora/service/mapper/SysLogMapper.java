package me.aurora.service.mapper;

import me.aurora.domain.SysLog;
import me.aurora.service.dto.SysLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 郑杰
 * @date 2018/08/28 7:06:05
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysLogMapper extends EntityMapper<SysLogDTO, SysLog>{

}
