package com.myConsole.modules.sys.dao;

import com.myConsole.modules.sys.entity.MyAboutEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 关于
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-23 17:14:55
 */
@Mapper
public interface MyAboutDao extends BaseMapper<MyAboutEntity> {
	
}
