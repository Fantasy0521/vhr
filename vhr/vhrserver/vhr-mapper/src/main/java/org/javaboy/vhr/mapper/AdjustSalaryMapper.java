package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.AdjustSalary;

import java.util.List;

public interface AdjustSalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdjustSalary record);

    int insertSelective(AdjustSalary record);

    AdjustSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdjustSalary record);

    int updateByPrimaryKey(AdjustSalary record);

    List<AdjustSalary> getAllAdjustSalaries();

    AdjustSalary getByEid(Integer eid);
}