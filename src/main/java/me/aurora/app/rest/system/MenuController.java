package me.aurora.app.rest.system;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.Menu;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.User;
import me.aurora.domain.vo.MenuVo;
import me.aurora.repository.spec.MenuSpec;
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
import java.util.List;
import java.util.Map;

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
    private MenuMapper menuMapper;

    /**
     * 构建前端路由
     */
    @GetMapping(value = "/buildMenuUrl")
    public Object buildMenuUrl(HttpServletRequest request){
        //查询出所有子菜单
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user = userService.findById(user.getId());
        List<MenuDTO> menuDTOS = menuService.findMenusByUserRols(user.getRoles());
        List<MenuVo> menuVoList = menuService.buildMenuUrl(menuDTOS);
        return menuVoList;
    }

    /**
     * 跳转到权限列表页面
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
    @Log("查询所有菜单")
    @RequiresPermissions(value={"admin", "menu:all","menu:select"}, logical= Logical.OR)
    @GetMapping(value = "/getMenusInfo")
    public Map getMenusInfo(@RequestParam(value = "id",required = false) Long id,
                            @RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        log.warn("REST request to findAll MenusInfo");
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
        log.warn("REST request to addPage");
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
    @PostMapping(value = "/inster")
    public ResponseEntity inster(@Validated(Menu.New.class) @RequestBody Menu menu, @RequestParam String roles){
        log.warn("REST request to inster menu");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Menu topMenu = null;
        if(menu.getPid() != 0){
            topMenu = menuService.findByPId(menu.getPid());
        }
        menuService.inster(menu,topMenu,roles);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "menu:all","menu:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        log.warn("REST request to toUpdatePage");
        Menu menu = menuService.findById(id);
        if(menu == null){
            return new ModelAndView("/exception/404");
        }
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
        log.warn("REST request to update");
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
        log.warn("REST request to delete menu");
        menuService.delete(menuService.findById(id));
        return ResponseEntity.ok();
    }
}
