package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.AdjustSalaryMapper;
import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.mapper.SalaryMapper;
import org.javaboy.vhr.model.AdjustSalary;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.Salary;
import org.javaboy.vhr.model.vo.SalaryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private AdjustSalaryMapper adjustSalaryMapper;

    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    public List<Salary> getSalariesByDepartment(Integer departmentId) {
        return salaryMapper.getSalariesByDepartment(departmentId);
    }

    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    public Integer updateSalaryById(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }

    public Salary getSalaryByEid(Integer eid) {
        return salaryMapper.getSalaryByEid(eid);
    }


    public List<SalaryVo> calculateSalaries() {
        List<Employee> employees = employeeMapper.getAllEmployees();
        List<AdjustSalary> adjustSalaries = adjustSalaryMapper.getAllAdjustSalaries();
        List<Salary> allSalaries = salaryMapper.getAllSalaries();

        Map<Integer, List<AdjustSalary>> adjustSalaryMap = adjustSalaries.stream()
                .collect(Collectors.groupingBy(AdjustSalary::getEid));
        Map<Integer, Salary> salaryMap = allSalaries.stream()
                .collect(Collectors.toMap(Salary::getId, salary -> salary));

        List<SalaryVo> calculatedSalaries = new ArrayList<>();

        for (Employee employee : employees) {
            SalaryVo salary = new SalaryVo();
            BeanUtils.copyProperties(employee.getSalary(), salary);
            salary.setEid(employee.getId());
            salary.setEname(employee.getName());

            // 获取最新的调薪记录
            List<AdjustSalary> employeeAdjustSalaries = adjustSalaryMap.get(employee.getId());
            AdjustSalary latestAdjustSalary = null;
            if (employeeAdjustSalaries != null && !employeeAdjustSalaries.isEmpty()) {
                latestAdjustSalary = employeeAdjustSalaries.stream()
                        .max((a1, a2) -> a1.getAsDate().compareTo(a2.getAsDate()))
                        .orElse(null);
            }

            if (latestAdjustSalary != null) {
                salary.setBasicSalary(latestAdjustSalary.getAfterSalary());
            } else {
                salary.setBasicSalary(employee.getSalary().getBasicSalary());
            }

            // 假设奖金、午餐补助和交通补助是固定的
//            salary.setBonus(500);
//            salary.setLunchSalary(100);
//            salary.setTrafficSalary(100);

            // 计算总工资
            salary.setAllSalary(salary.getBasicSalary() + salary.getBonus() + salary.getLunchSalary() + salary.getTrafficSalary());

            // 设置其他字段
//            salary.setPensionBase(salary.getBasicSalary());
//            salary.setPensionPer(0.08f);
//            salary.setMedicalBase(salary.getBasicSalary());
//            salary.setMedicalPer(0.02f);
//            salary.setAccumulationFundBase(salary.getBasicSalary());
//            salary.setAccumulationFundPer(0.05f);
//            salary.setCreateDate(new Date());
//            salary.setName(employee.getName());

            calculatedSalaries.add(salary);
        }

        return calculatedSalaries;
    }
}
