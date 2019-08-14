package com.myConsole.modules.sys.dao;

import com.myConsole.modules.sys.entity.MyArticleLabelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Mapper
public interface MyArticleLabelDao extends BaseMapper<MyArticleLabelEntity> {

    List<MyArticleLabelEntity> selectByBlogId(Integer blogId);
}
