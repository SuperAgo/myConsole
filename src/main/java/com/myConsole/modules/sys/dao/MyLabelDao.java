package com.myConsole.modules.sys.dao;

import com.myConsole.modules.sys.entity.MyLabelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文章分类标签
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Mapper
public interface MyLabelDao extends BaseMapper<MyLabelEntity> {

    List<Map> getSonLabelList();

    List<MyLabelEntity> getAlllabel();
}
