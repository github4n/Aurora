package me.aurora.app.rest.system;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.Permission;
import me.aurora.domain.ResponseEntity;
import me.aurora.repository.spec.PermissionSpec;
import me.aurora.service.PermissionService;
import me.aurora.service.mapper.PerMissionMapper;
import me.aurora.util.HttpContextUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/08/23 17:30:29
 */
@Slf4j
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PerMissionMapper perMissionMapper;

    @GetMapping(value = "/getAllPermissions")
    public Object getAllRole(){
        List<Map<String, Object>> permissionList = permissionService.buildPermissionTree(permissionService.findByPid(0));
        return permissionList;
    }

    /**
     * 跳转到权限列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/system/permission/index");
    }

    /**
     * 查询所有权限
     * @param id
     * @param perms
     * @param remark
     * @param page
     * @param limit
     * @return
     */
    @Log("查询所有权限")
    @RequiresPermissions(value={"admin", "permission:all","permission:select"}, logical= Logical.OR)
    @GetMapping(value = "/getPermissionsInfo")
    public Map getPermissionsInfo(@RequestParam(value = "id",required = false) Long id,
                                  @RequestParam(value = "perms",required = false) String perms,
                                  @RequestParam(value = "remark",required = false) String remark,
                                  @RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "limit",defaultValue = "2000")Integer limit){
        log.warn("REST request to findAll Permission");
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1,2000,sort);
        return permissionService.getPermissionInfo(new PermissionSpec(id,perms,remark),pageable);
    }

    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "permission:all","permission:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        log.warn("REST request to addPage");
        List<Permission> permissionList = permissionService.findByPid(0);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("topPermissions",permissionList);
        return new ModelAndView("/system/permission/add");
    }

    /**
     * 新增权限
     * @param permission
     * @return
     */
    @Log("新增权限")
    @RequiresPermissions (value={"admin", "permission:all","permission:add"}, logical= Logical.OR)
    @PostMapping(value = "/inster")
    public ResponseEntity inster(@RequestBody Permission permission) {
        log.warn("REST request to insterPermission");
        permissionService.inster(permission);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "permission:all","permission:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        log.warn("REST request to toUpdatePage");
        Permission permission = permissionService.findById(id);
        if(permission == null){
            return new ModelAndView("/exception/404");
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        List<Permission> permissionList = permissionService.findByPid(0);
        request.setAttribute("topPermissions",permissionList);
        request.setAttribute("permission",perMissionMapper.toDto(permission));
        return new ModelAndView("/system/permission/update");
    }

    /**
     * 更新权限
     * @param permission
     * @return
     */
    @Log("更新权限")
    @RequiresPermissions (value={"admin", "permission:all","permission:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@RequestBody Permission permission) {
        log.warn("REST request to insterUser");
        permissionService.update(permission);
        return ResponseEntity.ok();
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @Log("删除权限")
    @RequiresPermissions (value={"admin", "permission:all","permission:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.warn("REST request to deletePermission");
        permissionService.delete(id);
        return ResponseEntity.ok();
    }
}
