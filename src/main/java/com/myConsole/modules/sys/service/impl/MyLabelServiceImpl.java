package com.myConsole.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;

import com.myConsole.modules.sys.dao.MyLabelDao;
import com.myConsole.modules.sys.entity.MyLabelEntity;
import com.myConsole.modules.sys.service.MyLabelService;

import javax.annotation.Resource;


@Service("myLabelService")
public class MyLabelServiceImpl extends ServiceImpl<MyLabelDao, MyLabelEntity> implements MyLabelService {
    @Resource
    private MyLabelDao myLabelDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MyLabelEntity> page = this.page(
                new Query<MyLabelEntity>().getPage(params),
                new QueryWrapper<MyLabelEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map> getSonLabelList() {
        return myLabelDao.getSonLabelList();
    }

}
