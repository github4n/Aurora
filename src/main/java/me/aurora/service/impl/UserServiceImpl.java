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
import me.aurora.util.EncryptHelper;
import me.aurora.util.PageUtil;
import me.aurora.config.exception.AuroraException;
import me.aurora.util.ValidationUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 郑杰
 * @date 2018/08/23 11:54:10
 */
@Service
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
    @Transactional(readOnly = true)
    public User findById(Long id) {
        if(id == null){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"id not exist");
        }
        Optional<User> user = userRepo.findById(id);
        ValidationUtil.isNull(user,"id:"+id+"is not find");
        return user.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String userName) {
        return userRepo.findByUsername(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Map getUsersInfo(UserSpec userSpec, Pageable pageable) {
        Page<User> users = userRepo.findAll(userSpec,pageable);
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user:users.getContent()){
            UserDTO userDTO = userMapper.toDto(user,roleService.getRoles(user.getRoles()));
            userDTOS.add(userDTO);
        }
        return PageUtil.buildPage(userDTOS,users.getTotalElements());
    }

    @Override
    public void insert(User user, String roles) {
        if(userRepo.findByUsername(user.getUsername())!=null){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"用户名已存在");
        }
        if(userRepo.findByEmail(user.getEmail())!=null){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"用户邮箱已存在");
        }
        user.setPassword(EncryptHelper.encrypt(user.getPassword()));
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
        old.setPassword(EncryptHelper.encrypt(user.getPassword()));
        old.setUsername(user.getUsername());
        old.setEmail(user.getEmail());

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
    public void checkLastLoginTime(User user) {
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userRepo.save(user);
    }

    @Override
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
}
