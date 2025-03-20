package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeecMapper;
import org.javaboy.vhr.model.Employeeec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeecService {
    @Autowired
    EmployeeecMapper employeeecMapper;

    public List<Employeeec> getAllEmployeeecs() {
        return employeeecMapper.getAllEmployeeecs();
    }

    public int addEmployeeec(Employeeec employeeec) {
        return employeeecMapper.insert(employeeec);
    }

    public int deleteEmployeeecById(Integer id) {
        return employeeecMapper.deleteByPrimaryKey(id);
    }

    public int updateEmployeeecById(Employeeec employeeec) {
        return employeeecMapper.updateByPrimaryKeySelective(employeeec);
    }
}
