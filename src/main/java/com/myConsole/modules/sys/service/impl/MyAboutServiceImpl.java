package com.myConsole.modules.sys.service.impl;

import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;
import com.myConsole.modules.sys.dao.MyAboutDao;
import com.myConsole.modules.sys.entity.MyAboutEntity;
import com.myConsole.modules.sys.service.MyAboutService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("myAboutService")
public class MyAboutServiceImpl extends ServiceImpl<MyAboutDao, MyAboutEntity> implements MyAboutService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MyAboutEntity> page = this.page(
                new Query<MyAboutEntity>().getPage(params),
                new QueryWrapper<MyAboutEntity>()
        );

        return new PageUtils(page);
    }

}
