package me.aurora.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import me.aurora.domain.Menu;
import me.aurora.domain.Role;
import me.aurora.domain.vo.MenuVo;
import me.aurora.repository.MenuRepo;
import me.aurora.repository.spec.MenuSpec;
import me.aurora.service.MenuService;
import me.aurora.service.dto.MenuDTO;
import me.aurora.service.mapper.MenuMapper;
import me.aurora.util.ListSortUtil;
import me.aurora.util.PageUtil;
import me.aurora.config.exception.AuroraException;
import me.aurora.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 郑杰
 * @date 2018/08/23 17:27:57
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuVo> buildMenuUrl(List<MenuDTO> menuList) {
        List<MenuVo> menuVoList = new ArrayList<>();
        for (MenuDTO menu: menuList) {
            if(menu.getLevel()!=1){
                MenuVo menuVo = new MenuVo();
                menuVo.setPath(menu.getUrl());
                menuVo.setComponent(menu.getUrl());
                menuVo.setName(menu.getName());
                menuVoList.add(menuVo);
            }
        }
        return menuVoList;
    }

    @Override
    public List<MenuDTO> findMenusByUserRols(Set<Role> roles) {
        Set<Menu> menuSet = new HashSet<>(menuRepo.findByRoles(roles));
        List<Menu> menus = new ArrayList<>(menuSet);
        List<MenuDTO> menuDTOS = menuMapper.toDto(menus);
        ListSortUtil<MenuDTO> sortList = new ListSortUtil<MenuDTO>();
        sortList.sort(menuDTOS, "soft", "asc");
        return menuDTOS;
    }

    @Override
    public Map getMenuInfo(MenuSpec menuSpec, Pageable pageable) {
        Page<Menu> menuPage = menuRepo.findAll(menuSpec,pageable);
        Page<MenuDTO> menuDTOS = menuPage.map(menuMapper::toDto);
        return PageUtil.buildPage(menuDTOS.getContent(),menuPage.getTotalElements());
    }

    @Override
    public List<Menu> findAllMenus() {
        Integer level = 1;
        return menuRepo.findAllByLevel(level);
    }

    @Override
    public Menu findByPId(Integer pid) {
        Menu menu = menuRepo.findById(pid.longValue()).get();
        ValidationUtil.isNull(menu,"pid:"+pid+"is not find");
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void inster(Menu menu, Menu topMenu, String roles) {
        validation(menu);
        if(topMenu == null){
            menu.setLevel(1);
        } else {
            menu.setLevel(topMenu.getLevel()+1);
            topMenu.setLevelNum(topMenu.getLevelNum()+1);
            menuRepo.save(topMenu);
        }
        Set<Role> roleSet = new HashSet<>();
        for(String roleId:roles.split(",")){
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roleSet.add(role);
        }
        menu.setRoles(roleSet);
        menuRepo.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Menu menu) {
        if(menu.getLevelNum() == 0){
            menuRepo.delete(menu);
        } else {
            Set<Menu> menus = menuRepo.findByPid(menu.getPid()).stream().collect(Collectors.toSet());
            if(menus !=null && menus.size()!=0){
                menuRepo.deleteAll(menus);
            }
        }
    }

    @Override
    public Menu findById(Long id) {
        Menu menu = menuRepo.findById(id).get();
        ValidationUtil.isNull(menu,"id:"+id+"is not find");
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(Menu menu, Menu oldMenu, String roles) {
        validation(menu);
        Menu oldTopMenu = null;
        if(menu.getPid() != 0){
            /**
             * 都是两级菜单，并且上级类目不相同
             */
            if(oldMenu.getPid() != 0 && (!oldMenu.getPid().equals(menu.getPid()))){
                Menu topMenu = menuRepo.findById(menu.getPid().longValue()).get();
                oldTopMenu = menuRepo.findById(oldMenu.getPid().longValue()).get();
                oldMenu.setLevel(topMenu.getLevel()+1);
                oldMenu.setLevelNum(0);
                topMenu.setLevelNum(topMenu.getLevelNum()+1);
                oldTopMenu.setLevelNum(oldTopMenu.getLevelNum()-1);
                menuRepo.save(topMenu);
                menuRepo.save(oldTopMenu);

            /**
             * 新菜单是两级，旧菜单是一级
             */
            } else if(oldMenu.getPid() == 0){
                Menu topMenu = menuRepo.findById(menu.getPid().longValue()).get();
                topMenu.setLevelNum(topMenu.getLevelNum()+1);
                oldMenu.setLevel(topMenu.getLevel()+1);
                oldMenu.setLevelNum(0);
                menuRepo.save(topMenu);
            }
        /**
         * 新菜单是一级菜单，旧菜单是二级菜单
         */
        } else if(oldMenu.getPid() != 0){
            //设置新菜单为1级菜单
            oldMenu.setLevel(1);
            oldTopMenu = menuRepo.findById(oldMenu.getPid().longValue()).get();
            oldTopMenu.setLevelNum(oldTopMenu.getLevelNum()-1);
            menuRepo.save(oldTopMenu);
        }
        Set<Role> roleSet = new HashSet<>();
        for(String roleId:roles.split(",")){
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roleSet.add(role);
        }
        oldMenu.setRoles(roleSet);
        oldMenu.setIco(menu.getIco());
        oldMenu.setName(menu.getName());
        oldMenu.setPid(menu.getPid());
        oldMenu.setSoft(menu.getSoft());
        oldMenu.setUrl(menu.getUrl());
        menuRepo.save(oldMenu);
    }

    /**
     * 验证
     * @param menu
     */
    public void validation(Menu menu) {
        if(StrUtil.isEmpty(menu.getName())){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"菜单名称不能为空");
        }
        if(menu.getPid() != 0){
            if(StrUtil.isEmpty(menu.getUrl())){
                throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"链接地址不能为空");
            }
        }
        if(StrUtil.isEmpty(menu.getSoft().toString())){
            throw new AuroraException(HttpStatus.HTTP_NOT_FOUND,"排序序号不能为空");
        }  else {
            Menu menu1 = menuRepo.findBySoft(menu.getSoft());
            if (menu1 != null && !menu1.getId().equals(menu.getId())){
                throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"排序序号不能重复");
            }
        }
    }
}
