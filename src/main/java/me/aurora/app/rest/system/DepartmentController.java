package me.aurora.app.rest.system;

import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.Department;
import me.aurora.domain.ResponseEntity;
import me.aurora.repository.spec.DepartmentSpec;
import me.aurora.service.DepartmentService;
import me.aurora.service.RoleService;
import me.aurora.service.mapper.DepartmentMapper;
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
 * @date 2018/10/25 11:31:34
 */
@Slf4j
@RestController
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping(value = "/getAllDepartment")
    public Object getAllRole(@RequestParam(defaultValue = "true") Boolean isTop){
        List<Map<String, Object>> mapList = departmentService.buildDepartmentTree(departmentService.findByPid(0,isTop));
        return mapList;
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/system/department/index");
    }

    /**
     * 查询所有
     * @param id
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @Log("查询部门")
    @RequiresPermissions(value={"admin", "department:all","department:select"}, logical= Logical.OR)
    @GetMapping(value = "/getDepartmentsInfo")
    public Map getMenusInfo(@RequestParam(value = "id",required = false) Long id,
                            @RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1,2000,sort);
        return departmentService.getDepartmentsInfo(new DepartmentSpec(id,name),pageable);
    }

    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "department:all","department:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        return new ModelAndView("/system/department/add");
    }

    /**
     * 新增
     * @param department
     * @param roles
     * @return
     */
    @Log("新增部门")
    @RequiresPermissions (value={"admin", "department:all","department:add"}, logical= Logical.OR)
    @PostMapping(value = "/insert")
    public ResponseEntity insert(@Validated(Department.New.class) @RequestBody Department department, @RequestParam String roles){
        log.warn("REST request to insert Department : {} " + department);
        departmentService.insert(department,roles);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "department:all","department:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        Department department = departmentService.findById(id);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String rolesSelect = roleService.getRoles(department.getRoles());
        request.setAttribute("department",departmentMapper.toDto(department,rolesSelect));
        return new ModelAndView("/system/department/update");
    }

    /**
     * 更新
     * @param department
     * @param roles
     * @return
     */
    @Log("更新部门")
    @RequiresPermissions (value={"admin", "department:all","department:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@Validated(Department.Update.class) @RequestBody Department department,@RequestParam String roles) {
        log.warn("REST request to update Department : {} " + department);
        departmentService.update(department,departmentService.findById(department.getId()),roles);
        return ResponseEntity.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("删除部门")
    @RequiresPermissions (value={"admin", "department:all","department:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id){
        log.warn("REST request to delete Department : {}" +id);
        try{
            departmentService.delete(departmentService.findById(id));
        }catch (Exception e){
            if(e instanceof AuroraException){

                AuroraException auroraException = (AuroraException)e;
                throw auroraException;
            } else {
                throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"删除失败，部门存在员工");
            }
        }
        return ResponseEntity.ok();
    }
}
