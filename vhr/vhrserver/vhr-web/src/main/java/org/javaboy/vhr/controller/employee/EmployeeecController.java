package org.javaboy.vhr.controller.employee;

import org.javaboy.vhr.model.Employeeec;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.vo.EmployeeecVo;
import org.javaboy.vhr.service.EmployeeecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee/ec")
public class EmployeeecController {
    @Autowired
    EmployeeecService employeeecService;

    @GetMapping("/")
    public List<Employeeec> getAllEmployeeecs() {
        return employeeecService.getAllEmployeeecs();
    }

    @PostMapping("/")
    public RespBean addEmployeeec(@RequestBody Employeeec employeeec) {
        if (employeeecService.addEmployeeec(employeeec) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeecById(@PathVariable Integer id) {
        if (employeeecService.deleteEmployeeecById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmployeeecById(@RequestBody Employeeec employeeec) {
        if (employeeecService.updateEmployeeecById(employeeec) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @GetMapping("/getEmployeeecMonthInfo")
    public List<EmployeeecVo> getEmployeeecMonthInfo(String month) {
        return employeeecService.getEmployeeecMonthInfo(month);
    }
}
