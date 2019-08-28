package com.myConsole.modules.sys.dao;

import com.myConsole.modules.sys.entity.MyLeavingMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 用户留言表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Mapper
public interface MyLeavingMessageDao extends BaseMapper<MyLeavingMessageEntity> {

    Map getMyMessageBoard();

    int changeMessageBoards(Map myMessageBoard);
}
