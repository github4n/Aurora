package me.aurora.config.shiro;

import lombok.extern.slf4j.Slf4j;
import me.aurora.domain.Department;
import me.aurora.domain.Permission;
import me.aurora.domain.Role;
import me.aurora.domain.User;
import me.aurora.service.DepartmentService;
import me.aurora.service.PermissionService;
import me.aurora.service.UserService;
import me.aurora.util.ValidationUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 授权与认证
 * @author 郑杰
 * @date 2018/08/22 19:54:24
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

	private final Integer enabled = 0;

	@Autowired
	private UserService userService;

	@Autowired
    private DepartmentService departmentService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 获取用户角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();

		//获取用户详细信息
		user = userService.findById(user.getId());
		log.warn("=====>>>>>用户" + user.getUsername() + "获取权限");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 获取用户角色集
		Set<Role> roles = user.getRoles();

		// 获取部门信息
		Department department = departmentService.findById(user.getDepartment().getId());

        // 获取部门角色集
		roles.addAll(department.getRoles());
        Set<String> roleSet = roles.stream().map(Role::getName).collect(Collectors.toSet());
		simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
		Set<Permission> permissions = new HashSet(permissionService.findByRoles(roles));
        Set<String> permissionSet = permissions.stream().map(Permission::getPerms).collect(Collectors.toSet());

		simpleAuthorizationInfo.setStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		log.warn("=====>>>>>用户" + username + "认证");
		User user = null;
		if(ValidationUtil.isEmail(username)){
			user = userService.findByEmail(username);
		} else {
			user = userService.findByUsername(username);
		}
		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("用户名或密码错误！");
		}
		if (enabled.equals(user.getEnabled())) {
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

}
