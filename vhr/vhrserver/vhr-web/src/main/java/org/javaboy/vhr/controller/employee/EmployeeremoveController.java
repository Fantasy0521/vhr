package org.javaboy.vhr.controller.employee;

import org.javaboy.vhr.model.Employeeremove;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.EmployeeremoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/remove")
public class EmployeeremoveController {
    @Autowired
    EmployeeremoveService employeeremoveService;

    @GetMapping("/")
    public List<Employeeremove> getAllEmployeeremoves() {
        return employeeremoveService.getAllEmployeeremoves();
    }

    @PostMapping("/")
    public RespBean addEmployeeremove(@RequestBody Employeeremove employeeremove) {
        if (employeeremove == null || employeeremove.getRemovedate() == null){
            return RespBean.error("离职日期不能为空!");
        }
        //离职日期不能小于当前日期
        if (employeeremove.getRemovedate().before(new java.util.Date())) {
            return RespBean.error("离职日期不能小于当前日期!");
        }
        //判断是否该员工已提离职
        if (employeeremoveService.checkEmployeeremoveByEid(employeeremove.getEid())) {
            return RespBean.error("该员工已提离职!");
        }
        if (employeeremoveService.addEmployeeremove(employeeremove) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeremoveById(@PathVariable Integer id) {
        if (employeeremoveService.deleteEmployeeremoveById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmployeeremoveById(@RequestBody Employeeremove employeeremove) {
        if (employeeremoveService.updateEmployeeremoveById(employeeremove) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
