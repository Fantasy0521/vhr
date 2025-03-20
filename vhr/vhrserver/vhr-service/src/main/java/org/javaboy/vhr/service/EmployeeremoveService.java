package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeremoveMapper;
import org.javaboy.vhr.model.Employeeremove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeremoveService {
    @Autowired
    EmployeeremoveMapper employeeremoveMapper;

    public List<Employeeremove> getAllEmployeeremoves() {
        return employeeremoveMapper.getAllEmployeeremoves();
    }

    public int addEmployeeremove(Employeeremove employeeremove) {
        return employeeremoveMapper.insert(employeeremove);
    }

    public int deleteEmployeeremoveById(Integer id) {
        return employeeremoveMapper.deleteByPrimaryKey(id);
    }

    public int updateEmployeeremoveById(Employeeremove employeeremove) {
        return employeeremoveMapper.updateByPrimaryKeySelective(employeeremove);
    }

    public boolean checkEmployeeremoveByEid(Integer eid) {
        Employeeremove employeeremove = employeeremoveMapper.getByEid(eid);
        if (employeeremove != null && employeeremove.getRemovedate() != null){
            return true;
        }
        return false;
    }
}
