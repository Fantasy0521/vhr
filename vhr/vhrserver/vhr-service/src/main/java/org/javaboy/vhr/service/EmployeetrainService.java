package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeetrainMapper;
import org.javaboy.vhr.model.Employeetrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeetrainService {
    @Autowired
    EmployeetrainMapper employeetrainMapper;

    public List<Employeetrain> getAllEmployeetrains() {
        return employeetrainMapper.getAllEmployeetrains();
    }

    public int addEmployeetrain(Employeetrain employeetrain) {
        return employeetrainMapper.insert(employeetrain);
    }

    public int deleteEmployeetrainById(Integer id) {
        return employeetrainMapper.deleteByPrimaryKey(id);
    }

    public int updateEmployeetrainById(Employeetrain employeetrain) {
        return employeetrainMapper.updateByPrimaryKeySelective(employeetrain);
    }
}
