package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import me.aurora.config.AuroraProperties;
import me.aurora.domain.Role;
import me.aurora.domain.User;
import me.aurora.repository.UserRepo;
import me.aurora.repository.spec.UserSpec;
import me.aurora.service.RoleService;
import me.aurora.service.UserService;
import me.aurora.service.dto.UserDTO;
import me.aurora.service.mapper.UserMapper;
import me.aurora.util.AuroraConstant;
import me.aurora.util.EncryptHelper;
import me.aurora.util.PageUtil;
import me.aurora.config.exception.AuroraException;
import me.aurora.util.ValidationUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 郑杰
 * @date 2018/08/23 11:54:10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuroraProperties auroraProperties;

    @Autowired
    private RoleService roleService;

    @Override
    public User findById(Long id) {
        if(id == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"id not exist");
        }
        Optional<User> user = userRepo.findById(id);
        ValidationUtil.isNull(user,"id:"+id+"is not find");
        return user.get();
    }

    @Override
    public User findByUsername(String userName) {
        return userRepo.findByUsername(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Map getUsersInfo(UserSpec userSpec, Pageable pageable) {
        Page<User> users = userRepo.findAll(userSpec,pageable);
        List<UserDTO> userDTOS = new ArrayList<>();
        users.getContent().forEach(user -> {
            UserDTO userDTO = userMapper.toDto(user,roleService.getRoles(user.getRoles()));
            userDTOS.add(userDTO);
        });
        return PageUtil.buildPage(userDTOS,users.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(User user, String roles) {
        if(userRepo.findByUsername(user.getUsername())!=null){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"用户名已存在");
        }
        if(userRepo.findByEmail(user.getEmail())!=null){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"用户邮箱已存在");
        }
        user.setPassword(AuroraConstant.PWD);
        Set<Role> roleSet = new HashSet<>();
        for(String roleId:roles.split(",")){
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        user.setAvatar(auroraProperties.getUserAvatar());
        userRepo.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user, String roles) {
        User old = userRepo.findByUsername(user.getUsername());
        if(old!=null&&!old.getId().equals(user.getId())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"用户名已存在");
        }
        old = userRepo.findByEmail(user.getEmail());
        if(old!=null&&!old.getId().equals(user.getId())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"用户邮箱已存在");
        }
        old = userRepo.findById(user.getId()).get();
        old.setUsername(user.getUsername());
        old.setEmail(user.getEmail());
        old.setDepartment(user.getDepartment());

        Set<Role> roleSet = new HashSet<>();
        for(String roleId:roles.split(",")){
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roleSet.add(role);
        }
        old.setRoles(roleSet);
        userRepo.save(old);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(User user) {
        if(user == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"用户不存在，请检查缓存！！");
        }
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(u.getId().equals(user.getId())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"这么狠，自己都删！！");
        }
        userRepo.delete(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEnabled(User user) {
        if(user == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"用户不存在，请检查缓存！！");
        }
        User u = (User) SecurityUtils.getSubject().getPrincipal();
        if(user.getId().equals(u.getId())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"不能禁用自己");
        }
        user.setEnabled(user.getEnabled()==1?0:1);
        userRepo.save(user);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }
}
