package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.RoleMapper;
import org.javaboy.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @时间 2019-10-01 19:41
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
