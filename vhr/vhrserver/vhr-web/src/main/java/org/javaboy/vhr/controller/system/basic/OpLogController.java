package org.javaboy.vhr.controller.system.basic;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.model.vo.OpLogVo;
import org.javaboy.vhr.service.OpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/system/basic/oplog")
public class OpLogController {
    @Autowired
    OpLogService opLogService;


    @GetMapping("/")
    public RespPageBean getAllOpLogs(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return opLogService.getAllOpLogs(page, size);
    }
}
