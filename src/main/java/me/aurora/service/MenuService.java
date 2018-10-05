package me.aurora.service;

import me.aurora.domain.Menu;
import me.aurora.domain.Role;
import me.aurora.domain.vo.MenuVo;
import me.aurora.repository.spec.MenuSpec;
import me.aurora.service.dto.MenuDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018/08/23 17:27:57
 */
@CacheConfig(cacheNames = "menu")
public interface MenuService {

    /**
     * 返回前端路由所需要的子菜单数据
     * @param menuList
     * @return
     */
    List<MenuVo> buildMenuUrl(List<MenuDTO> menuList);

    /**
     * 根据Role获取所有菜单
     * @param roles
     * @return
     */
    List<MenuDTO> findMenusByUserRols(Set<Role> roles);

    /**
     * 查询所有菜单
     * @param menuSpec
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Map getMenuInfo(MenuSpec menuSpec, Pageable pageable);

    /**
     * 查询所有菜单
     * @return
     */
    @Cacheable(key = "'findAllMenus'")
    List<Menu> findAllMenus();

    /**
     * 新增菜单
     * @param menu
     * @param topMenu
     * @param roles
     */
    @CacheEvict(allEntries = true)
    void insert(Menu menu, Menu topMenu, String roles);

    /**
     * 根据PID查询菜单
     * @param pid
     * @return
     */
    @Cacheable(key = "#p0")
    Menu findByPId(Integer pid);

    /**
     * 删除菜单
     * @param menu
     */
    @CacheEvict(allEntries = true)
    void delete(Menu menu);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Menu findById(Long id);

    /**
     * 更新菜单
     * @param menu
     * @param oldMenu
     * @param roles
     */
    @CacheEvict(allEntries = true)
    void update(Menu menu, Menu oldMenu, String roles);
}
