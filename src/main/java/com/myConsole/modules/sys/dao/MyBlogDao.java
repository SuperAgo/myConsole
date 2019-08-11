package com.myConsole.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myConsole.modules.sys.entity.MyBlogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户博客表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Mapper
public interface MyBlogDao extends BaseMapper<MyBlogEntity> {
    IPage<MyBlogEntity> page(IPage<MyBlogEntity> page,@Param("params") Map params);

    MyBlogEntity selectById(Integer id);

    Integer updateByPrimaryKeySelective(MyBlogEntity myBlog);

    int insertSelective(MyBlogEntity myBlog);
}
