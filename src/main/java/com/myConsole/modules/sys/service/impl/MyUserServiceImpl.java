package com.myConsole.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;

import com.myConsole.modules.sys.dao.MyUserDao;
import com.myConsole.modules.sys.entity.MyUserEntity;
import com.myConsole.modules.sys.service.MyUserService;


@Service("myUserService")
public class MyUserServiceImpl extends ServiceImpl<MyUserDao, MyUserEntity> implements MyUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MyUserEntity> page = this.page(
                new Query<MyUserEntity>().getPage(params),
                new QueryWrapper<MyUserEntity>()
        );

        return new PageUtils(page);
    }

}
