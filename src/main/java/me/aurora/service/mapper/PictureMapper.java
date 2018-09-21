package me.aurora.service.mapper;

import me.aurora.domain.Picture;
import me.aurora.service.dto.PictureDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author 郑杰
 * @date 2018/09/20 14:09:43
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PictureMapper extends EntityMapper<PictureDto, Picture>{

    @Mapping(target = "userName",source = "picture.user.username")
    PictureDto toDto(Picture picture);
}
