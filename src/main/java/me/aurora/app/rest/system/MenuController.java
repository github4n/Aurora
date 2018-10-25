package me.aurora.app.rest.system;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.*;
import me.aurora.domain.vo.MenuVo;
import me.aurora.repository.spec.MenuSpec;
import me.aurora.service.DepartmentService;
import me.aurora.service.MenuService;
import me.aurora.service.RoleService;
import me.aurora.service.UserService;
import me.aurora.service.dto.MenuDTO;
import me.aurora.service.mapper.MenuMapper;
import me.aurora.util.HttpContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/23 17:27:57
 */
@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 构建前端路由
     */
    @GetMapping(value = "/buildMenuUrl")
    public Object buildMenuUrl(){
        //查询出所有子菜单
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user = userService.findById(user.getId());

        // 获取部门信息，获取部门的角色菜单
        Department department = departmentService.findById(user.getDepartment().getId());

        Set<Role> roleSet = user.getRoles();
        roleSet.addAll(department.getRoles());
        List<MenuDTO> menuDTOS = menuService.findMenusByUserRols(roleSet);
        List<MenuVo> menuVoList = menuService.buildMenuUrl(menuDTOS);
        return menuVoList;
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/system/menu/index");
    }

    /**
     * 查询所有菜单
     * @param id
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @Log("查询菜单")
    @RequiresPermissions(value={"admin", "menu:all","menu:select"}, logical= Logical.OR)
    @GetMapping(value = "/getMenusInfo")
    public Map getMenusInfo(@RequestParam(value = "id",required = false) Long id,
                            @RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(page-1,2000,sort);
        return menuService.getMenuInfo(new MenuSpec(id,name),pageable);
    }


    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "menu:all","menu:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        List<Menu> menus = menuService.findAllMenus();
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        request.setAttribute("topMenus",menus);
        return new ModelAndView("/system/menu/add");
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @Log("新增菜单")
    @RequiresPermissions (value={"admin", "menu:all","menu:add"}, logical= Logical.OR)
    @PostMapping(value = "/insert")
    public ResponseEntity insert(@Validated(Menu.New.class) @RequestBody Menu menu, @RequestParam String roles){
        log.warn("REST request to insert Menu : {} " + menu);
        Menu topMenu = null;
        if(menu.getPid() != 0){
            topMenu = menuService.findByPId(menu.getPid());
        }
        menuService.insert(menu,topMenu,roles);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "menu:all","menu:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        Menu menu = menuService.findById(id);
        List<Menu> menus = menuService.findAllMenus();
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String rolesSelect = roleService.getRoles(menu.getRoles());
        request.setAttribute("topMenus",menus);
        request.setAttribute("menu",menuMapper.toDto(menu,rolesSelect));
        return new ModelAndView("/system/menu/update");
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @Log("更新菜单")
    @RequiresPermissions (value={"admin", "menu:all","menu:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@Validated(Menu.Update.class) @RequestBody Menu menu,@RequestParam String roles) {
        log.warn("REST request to update Menu : {} " + menu);
        menuService.update(menu,menuService.findById(menu.getId()),roles);
        return ResponseEntity.ok();
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Log("删除菜单")
    @RequiresPermissions (value={"admin", "menu:all","menu:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id){
        log.warn("REST request to delete Menu : {}" +id);
        menuService.delete(menuService.findById(id));
        return ResponseEntity.ok();
    }
}
