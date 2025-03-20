package org.javaboy.vhr.controller.salary;

import org.javaboy.vhr.model.AdjustSalary;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.AdjustSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary/adjust")
public class AdjustSalaryController {
    @Autowired
    AdjustSalaryService adjustSalaryService;

    @GetMapping("/")
    public List<AdjustSalary> getAllAdjustSalaries() {
        return adjustSalaryService.getAllAdjustSalaries();
    }

    @PostMapping("/")
    public RespBean addAdjustSalary(@RequestBody AdjustSalary adjustSalary) {
        //判断是否已提调薪
        if (adjustSalaryService.checkAdjustSalaryByEid(adjustSalary.getEid())) {
            return RespBean.error("该员工已提调薪!");
        }
        if (adjustSalaryService.addAdjustSalary(adjustSalary) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteAdjustSalaryById(@PathVariable Integer id) {
        if (adjustSalaryService.deleteAdjustSalaryById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateAdjustSalaryById(@RequestBody AdjustSalary adjustSalary) {
        if (adjustSalaryService.updateAdjustSalaryById(adjustSalary) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}
