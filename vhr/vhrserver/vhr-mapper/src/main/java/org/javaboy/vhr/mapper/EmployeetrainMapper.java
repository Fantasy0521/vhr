package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.Employeetrain;

import java.util.List;

public interface EmployeetrainMapper {

    List<Employeetrain> getAllEmployeetrains();
    int deleteByPrimaryKey(Integer id);

    int insert(Employeetrain record);

    int insertSelective(Employeetrain record);

    Employeetrain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeetrain record);

    int updateByPrimaryKey(Employeetrain record);
}