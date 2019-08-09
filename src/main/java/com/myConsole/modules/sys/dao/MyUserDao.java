package com.myConsole.modules.sys.dao;

import com.myConsole.modules.sys.entity.MyUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Mapper
public interface MyUserDao extends BaseMapper<MyUserEntity> {
	
}
