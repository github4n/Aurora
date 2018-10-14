package me.aurora.app.rest.user;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.domain.User;
import me.aurora.repository.spec.UserSpec;
import me.aurora.service.RoleService;
import me.aurora.service.UserService;
import me.aurora.service.mapper.UserMapper;
import me.aurora.util.EncryptHelper;
import me.aurora.util.HttpContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户信息
 * @author 郑杰
 * @date 2018/08/22 19:38:17
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到用户列表页面
     * @return
     */
    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("/user/index");
    }

    /**
     * 查询所有用户
     * @param id
     * @param username
     * @param email
     * @param enabled
     * @param page
     * @param limit
     * @return
     */
    @Log("查询所有用户")
    @RequiresPermissions (value={"admin", "user:all","user:select"}, logical= Logical.OR)
    @GetMapping(value = "/getUsersInfo")
    public Map getUsersInfo(@RequestParam(value = "id",required = false) Long id,
                                @RequestParam(value = "username",required = false) String username,
                                @RequestParam(value = "email",required = false) String email,
                                @RequestParam(value = "enabled",required = false) Long enabled,
                                @RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        return userService.getUsersInfo(new UserSpec(username,email,enabled,id),pageable);
    }

    /**
     * 去新增页面
     * @return
     */
    @RequiresPermissions (value={"admin", "user:all","user:add"}, logical= Logical.OR)
    @GetMapping(value = "/toAddPage")
    public ModelAndView toAddPage(){
        return new ModelAndView("/user/add");
    }

    /**
     * 新增用户
     * @param user
     * @param roles
     * @return
     */
    @Log("新增用户")
    @RequiresPermissions (value={"admin", "user:all","user:add"}, logical= Logical.OR)
    @PostMapping(value = "/insert")
    public ResponseEntity insert(@Validated(User.New.class) @RequestBody User user, @RequestParam String roles) {
        log.warn("REST request to insert User : {}" +user);
        if(StrUtil.hasEmpty(roles)){
            return ResponseEntity.error(HttpStatus.HTTP_NOT_FOUND,"角色为空，请至少为其分配一个角色");
        }
        userService.insert(user,roles);
        return ResponseEntity.ok();
    }

    /**
     * 去编辑页面
     * @return
     */
    @RequiresPermissions (value={"admin", "user:all","user:update"}, logical= Logical.OR)
    @GetMapping(value = "/toUpdatePage")
    public ModelAndView toUpdatePage(@RequestParam Long id){
        User user = userService.findById(id);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String rolesSelect = roleService.getRoles(user.getRoles());
        request.setAttribute("editUser", userMapper.toDto(user,rolesSelect));
        return new ModelAndView("/user/update");
    }

    /**
     * 更新用户
     * @param user
     * @param roles
     * @return
     */
    @Log("更新用户")
    @RequiresPermissions (value={"admin", "user:all","user:update"}, logical= Logical.OR)
    @PutMapping(value = "/update")
    public ResponseEntity update(@Validated(User.Update.class) @RequestBody User user, @RequestParam String roles) {
        log.warn("REST request to update User : {}" +user);
        if(StrUtil.hasEmpty(roles)){
            return ResponseEntity.error(HttpStatus.HTTP_NOT_FOUND,"角色为空，请至少为其分配一个角色");
        }
        userService.update(user,roles);
        return ResponseEntity.ok();
    }

    /**
     * 是否允许登录
     * @param id
     * @return
     */
    @Log("设置用户账号是否允许登录")
    @RequiresPermissions (value={"admin", "user:all","user:lock"}, logical= Logical.OR)
    @PutMapping(value = "/updateEnabled")
    public ResponseEntity updateEnabled(@RequestParam Long id){
        log.warn("REST request to updateEnabled User : {}" +id);
        userService.updateEnabled(userService.findById(id));
        return ResponseEntity.ok();
    }

    /**
     * 锁屏页面
     * @return
     */
    @GetMapping(value = "/lock.html")
    public ModelAndView lockPage(HttpServletRequest request){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user",user);
        return new ModelAndView("/user/lock");
    }

    /**
     * 锁屏
     * @return
     */
    @GetMapping(value = "/lock")
    public ResponseEntity lock(HttpServletRequest request,@RequestParam String url){
        request.getSession(true).setAttribute("lock",url);
        return ResponseEntity.ok();
    }

    /**
     * 解锁
     * @return
     */
    @PostMapping(value = "/unlock")
    public ResponseEntity unlock(@RequestParam String password){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        password = EncryptHelper.encrypt(password);
        if(password.equals(user.getPassword())){
            /**
             * 将锁屏清掉
             */
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            String url = request.getSession(true).getAttribute("lock").toString();
            request.getSession(true).setAttribute("lock",null);
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.error("密码错误");
    }

    /**
     * 删除User
     * @param id
     * @return
     */
    @Log("删除用户")
    @RequiresPermissions (value={"admin", "user:all","user:delete"}, logical= Logical.OR)
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.warn("REST request to delete User : {}" +id);
        userService.delete(userService.findById(id));
        return ResponseEntity.ok();
    }
}
