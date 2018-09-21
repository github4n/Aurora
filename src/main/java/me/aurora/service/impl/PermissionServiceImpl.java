package me.aurora.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import me.aurora.domain.Permission;
import me.aurora.domain.Role;
import me.aurora.repository.PermissionRepo;
import me.aurora.repository.spec.PermissionSpec;
import me.aurora.service.PermissionService;
import me.aurora.service.dto.PermissionDTO;
import me.aurora.service.mapper.PerMissionMapper;
import me.aurora.util.PageUtil;
import me.aurora.config.exception.AuroraException;
import me.aurora.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author 郑杰
 * @date 2018/08/23 17:30:58
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private PerMissionMapper perMissionMapper;

    @Override
    public Map getPermissionInfo(PermissionSpec permissionSpec, Pageable pageable) {
        Page<Permission> permissionPage = permissionRepo.findAll(permissionSpec,pageable);
        Page<PermissionDTO> permissionDTOS = permissionPage.map(perMissionMapper::toDto);
        return PageUtil.buildPage(permissionDTOS.getContent(),permissionPage.getTotalElements());
    }

    @Override
    public void inster(Permission permission) throws AuroraException {
        if(StrUtil.hasEmpty(permission.getPerms())){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"权限名称不能为空");
        }
        if(permissionRepo.findByPerms(permission.getPerms())!=null){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"权限已存在");
        }
        permissionRepo.save(permission);
    }

    @Override
    public Permission findById(Long id) {
        Permission permission = permissionRepo.findById(id).get();
        ValidationUtil.isNull(permission,"id:"+id+"is not find");
        return permission;
    }

    @Override
    public void update(Permission permission) throws AuroraException {
        if(null == permission.getId()){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"权限ID不能为空");
        }
        if(StrUtil.isEmpty(permission.getPerms())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"权限名称不能为空");
        }
        Permission old = permissionRepo.findByPerms(permission.getPerms());
        if(old !=null && !old.getId().equals(permission.getId())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"权限已存在");
        }
        permissionRepo.save(permission);
    }

    @Override
    public void delete(Permission permission) {
        if(permission.getRoles() != null && permission.getRoles().size()!=0){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"暂不支持删除使用中的权限，请先与角色解绑");
        }
        permissionRepo.delete(permission);
    }

    @Override
    public List<Permission> getAll() {
        return permissionRepo.findAll();
    }

    @Override
    public List<Map<String, Object>> buildPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> maps = new LinkedList<>();
        for (Permission permission:permissions) {
            if (permission!=null){
                List<Permission> permissionList = permissionRepo.findByPid(Integer.parseInt(permission.getId()+""));
                Map<String,Object> map = new HashMap<>(16);
                map.put("id",permission.getId());
                String name = !StrUtil.isEmpty(permission.getRemark())?permission.getRemark():permission.getPerms();
                map.put("name",name);
                if(permissionList!=null && permissionList.size()!=0){
                    map.put("open",true);
                    map.put("isParent",true);
                    map.put("children",buildPermissionTree(permissionList));
                }
                maps.add(map);
            }
        }
        return maps;
    }

    @Override
    public List<Permission> findByPid(int pid) {
        return permissionRepo.findByPid(pid);
    }

    @Override
    public List<Permission> findByRoles(Set<Role> roles) {
        return permissionRepo.findByRoles(roles);
    }
}
