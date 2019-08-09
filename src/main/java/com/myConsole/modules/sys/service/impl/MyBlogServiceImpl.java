package com.myConsole.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;

import com.myConsole.modules.sys.dao.MyBlogDao;
import com.myConsole.modules.sys.entity.MyBlogEntity;
import com.myConsole.modules.sys.service.MyBlogService;

import javax.annotation.Resource;


@Service("myBlogService")
public class MyBlogServiceImpl extends ServiceImpl<MyBlogDao, MyBlogEntity> implements MyBlogService {
    @Resource
    private MyBlogDao myBlogDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MyBlogEntity> page = myBlogDao.page(
                new Query<MyBlogEntity>().getPage(params),
                params
        );
        return new PageUtils(page);
    }

}
