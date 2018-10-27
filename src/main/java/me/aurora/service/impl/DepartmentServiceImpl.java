package me.aurora.service.impl;

import me.aurora.domain.Department;
import me.aurora.domain.Role;
import me.aurora.repository.DepartmentRepo;
import me.aurora.repository.spec.DepartmentSpec;
import me.aurora.service.DepartmentService;
import me.aurora.service.dto.DepartmentDTO;
import me.aurora.service.mapper.DepartmentMapper;
import me.aurora.util.PageUtil;
import me.aurora.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 郑杰
 * @date 2018/10/25 11:32:42
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> findByPid(int pid, Boolean isTop) {
        List<Department> list = new ArrayList<>();
        if(isTop){
            Department department = new Department();
            department.setId(0L);
            department.setName("顶级部门");
            department.setPid(null);
            list.add(department);
        } else {
            list.addAll(departmentRepo.findByPid(pid));
        }
        return list;
    }

    @Override
    public Map getDepartmentsInfo(DepartmentSpec departmentSpec, Pageable pageable) {

        Page<Department> menuPage = departmentRepo.findAll(departmentSpec,pageable);
        Page<DepartmentDTO> departmentDTOS = menuPage.map(departmentMapper::toDto);
        return PageUtil.buildPage(departmentDTOS.getContent(),menuPage.getTotalElements());
    }

    @Override
    public List<Map<String, Object>> buildDepartmentTree(List<Department> departments) {
        List<Map<String,Object>> maps = new LinkedList<>();
        departments.forEach(department -> {
                    if (department!=null){
                        List<Department> departmentList = new ArrayList<>();
                        Map<String,Object> map = new HashMap<>(16);
                        map.put("id",department.getId());
                        map.put("name",department.getName());
                        map.put("pid",department.getPid());
                        if(department.getId().equals(0L)){
                            departmentList = departmentRepo.findByPid(0);
                        } else {
                            departmentList = departmentRepo.findByPid(Integer.parseInt(department.getId()+""));
                        }
                        if(departmentList!=null && departmentList.size()!=0){

                            map.put("children",buildDepartmentTree(departmentList));
                        }
                        maps.add(map);
                    }
                }
        );
        return maps;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Department department, String roles) {
        Set<Role> roleSet = new HashSet<>();
        for(String roleId:roles.split(",")){
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roleSet.add(role);
        }
        department.setRoles(roleSet);
        departmentRepo.save(department);
    }

    @Override
    public Department findById(Long id) {
        Optional<Department> department = departmentRepo.findById(id);
        ValidationUtil.isNull(department,"Department");
        return department.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Department department,Department old, String roles) {
        Set<Role> roleSet = new HashSet<>();
        for(String roleId:roles.split(",")){
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roleSet.add(role);
        }
        department.setCreateTime(old.getCreateTime());
        department.setRoles(roleSet);
        departmentRepo.save(department);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Department department) {

        if(department.getPid() == 0){

            List<Department> departmentList = departmentRepo.findByPid(Integer.parseInt(department.getId()+""));
            departmentList.add(department);
            departmentRepo.deleteAll(departmentList);
        } else {
            departmentRepo.delete(department);
        }
    }
}
