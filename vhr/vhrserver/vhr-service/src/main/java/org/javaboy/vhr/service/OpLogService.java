package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.OpLogMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.OpLog;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.model.vo.OpLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpLogService {
    @Autowired
    OpLogMapper opLogMapper;

    public RespPageBean getAllOpLogs(Integer page, Integer size) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<OpLogVo> data = opLogMapper.getAllOpLogs(page, size);
        Integer maxSize = 10000;
        List<OpLogVo> totalData = opLogMapper.getAllOpLogs(page, maxSize);
        Long total = (long) totalData.size();
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }
}
