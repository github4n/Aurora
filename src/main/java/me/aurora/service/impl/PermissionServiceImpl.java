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
import me.aurora.util.job.ScheduleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void insert(Permission permission) throws AuroraException {
        if(permissionRepo.findByPerms(permission.getPerms())!=null){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"权限已存在");
        }
        permissionRepo.save(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public Permission findById(Long id) {
        if(id == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"id not exist");
        }
        Optional<Permission> permission = permissionRepo.findById(id);
        ValidationUtil.isNull(permission,"id:"+id+"is not find");
        return permission.get();
    }

    @Override
    public void update(Permission old, Permission permission) throws AuroraException {
        Permission permission1 = permissionRepo.findByPerms(permission.getPerms());
        if(permission1 !=null && !permission1.getId().equals(permission.getId())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"权限已存在");
        }
        permission.setCreateTime(old.getCreateTime());
        permissionRepo.save(permission);
    }

    @Override
    public void delete(Permission permission) {
        try {
            permissionRepo.delete(permission);
        }catch (Exception e){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"删除失败，请检查权限是否被使用");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> getAll() {
        return permissionRepo.findAll();
    }

    @Override
    public List<Map<String, Object>> buildPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> maps = new LinkedList<>();
        permissions.forEach(permission -> {
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
        );
        return maps;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findByPid(int pid) {
        return permissionRepo.findByPid(pid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> findByRoles(Set<Role> roles) {
        return permissionRepo.findByRoles(roles);
    }
}
