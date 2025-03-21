package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.model.OpLog;
import org.javaboy.vhr.model.vo.OpLogVo;

import java.util.List;

public interface OpLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OpLog record);

    int insertSelective(OpLog record);

    OpLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpLog record);

    int updateByPrimaryKey(OpLog record);

    List<OpLogVo> getAllOpLogs(@Param("page") Integer page, @Param("size") Integer size);

}