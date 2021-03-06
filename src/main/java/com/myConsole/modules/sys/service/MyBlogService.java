package com.myConsole.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.modules.sys.entity.MyBlogEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户博客表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
public interface MyBlogService extends IService<MyBlogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MyBlogEntity selectById(Integer id);

    Integer updateByPrimaryKeySelective(MyBlogEntity myBlog);

    int insertSelective(MyBlogEntity myBlog);

    int setTop(Integer id);

    int setSelected(Integer id);

    int deleteByIds(List<Integer> ids);
}

