package me.aurora.app.rest.system;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.Role;
import me.aurora.repository.spec.RoleSpec;
import me.aurora.service.RoleService;
import me.aurora.service.mapper.RoleMapper;
import me.aurora.util.HttpContextUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/08/23 17:25:12
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping(value = "/getAllRole")
    public Object getAllRole(){
        List<Map<String, Object>> mapList = roleService.buildRoleTree(roleService.getAllRole());
        return mapList;
    }

    /**
     * 跳转到角色列表
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/system/role/index");
    }

    /**
     * 查询所有角色
     * @param id
     * @param name
     * @param remark
     * @param page
     * @param limit
     * @return
     */
    @Log("查询所有角色")
    @RequiresPermissions(value={"admin", "role:all","role:select"}, logical= Logical.OR)
    @GetMapping(value = "/getRolesInfo")
    public Map getRolesInfo(@RequestParam(value = "id",required = false) Long id,
                            @RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "remark",required = false) String remark,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return roleService.getRoleInfo(new RoleSpec(id,name,remark),pageable);
    }

    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "role:all","role:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        log.warn("REST request to addPage");
        return new ModelAndView("/system/role/add");
    }

    /**
     * 新增角色
     * @param role
     * @param permissions
     * @return
     */
    @Log("新增角色")
    @RequiresPermissions (value={"admin", "role:all","role:add"}, logical= Logical.OR)
    @PostMapping(value = "/insert")
    public ResponseEntity insert(@Validated(Role.New.class) @RequestBody Role role, @RequestParam String permissions) {
        log.warn("REST request to insert Role : {}" +role);
        roleService.insert(role,permissions);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "role:all","role:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        Role role = roleService.findById(id);
        String permissionSelect = roleService.getPermissions(role.getPermissions());
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("role", roleMapper.toDto(role));
        request.setAttribute("permissionSelect", permissionSelect);
        return new ModelAndView("/system/role/update");
    }

    /**
     * 更新角色
     * @param role
     * @param permissions
     * @return
     */
    @Log("更新角色")
    @RequiresPermissions (value={"admin", "role:all","role:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@Validated(Role.Update.class) @RequestBody Role role,@RequestParam String permissions) {
        log.warn("REST request to update Role : {}" +role);
        roleService.update(roleService.findById(role.getId()),role,permissions);
        return ResponseEntity.ok();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Log("删除角色")
    @RequiresPermissions (value={"admin", "role:all","role:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.warn("REST request to delete Role : {}" +id);
        roleService.delete(roleService.findById(id));
        return ResponseEntity.ok();
    }
}
