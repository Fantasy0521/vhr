package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.AdjustSalaryMapper;
import org.javaboy.vhr.model.AdjustSalary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdjustSalaryService {
    @Autowired
    AdjustSalaryMapper adjustSalaryMapper;

    public List<AdjustSalary> getAllAdjustSalaries() {
        return adjustSalaryMapper.getAllAdjustSalaries();
    }

    public int addAdjustSalary(AdjustSalary adjustSalary) {
        return adjustSalaryMapper.insert(adjustSalary);
    }

    public int deleteAdjustSalaryById(Integer id) {
        return adjustSalaryMapper.deleteByPrimaryKey(id);
    }

    public int updateAdjustSalaryById(AdjustSalary adjustSalary) {
        return adjustSalaryMapper.updateByPrimaryKeySelective(adjustSalary);
    }

    public boolean checkAdjustSalaryByEid(Integer eid) {
        if (adjustSalaryMapper.getByEid(eid) != null) {
            return true;
        }
        return false;
    }
}
