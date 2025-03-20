package org.javaboy.vhr.controller.employee;

import org.javaboy.vhr.model.Employeetrain;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.EmployeetrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/train")
public class EmployeetrainController {
    @Autowired
    EmployeetrainService employeetrainService;

    @GetMapping("/")
    public List<Employeetrain> getAllEmployeetrains() {
        return employeetrainService.getAllEmployeetrains();
    }

    @PostMapping("/")
    public RespBean addEmployeetrain(@RequestBody Employeetrain employeetrain) {
        if (employeetrainService.addEmployeetrain(employeetrain) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmployeetrainById(@PathVariable Integer id) {
        if (employeetrainService.deleteEmployeetrainById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmployeetrainById(@RequestBody Employeetrain employeetrain) {
        if (employeetrainService.updateEmployeetrainById(employeetrain) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
