package me.aurora.service.mapper;

import me.aurora.domain.User;
import me.aurora.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author 郑杰
 * @date 2018/08/22 11:54:10
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User>{

    /**
     * 自定义转换
     * @param user
     * @param rolesSelect
     * @return
     */
    @Mapping(source = "rolesSelect",target = "rolesSelect")
    @Mapping(source = "user.department.name",target = "departmentName")
    @Mapping(source = "user.sex",target = "sex")
    @Mapping(source = "user.birthday",target = "birthday")
    UserDTO toDto(User user,String rolesSelect);

    /**
     * 自定义转换
     * @param user
     * @return
     */
    @Mapping(source = "user.department.name",target = "departmentName")
    @Override
    UserDTO toDto(User user);


}
