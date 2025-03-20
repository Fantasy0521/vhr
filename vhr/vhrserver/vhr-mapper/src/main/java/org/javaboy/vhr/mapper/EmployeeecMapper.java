package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.Employeeec;

import java.util.List;

public interface EmployeeecMapper {

    List<Employeeec> getAllEmployeeecs();
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeec record);

    int insertSelective(Employeeec record);

    Employeeec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeec record);

    int updateByPrimaryKey(Employeeec record);
}