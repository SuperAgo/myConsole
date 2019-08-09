package com.myConsole.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;

import com.myConsole.modules.sys.dao.MyArticleLabelDao;
import com.myConsole.modules.sys.entity.MyArticleLabelEntity;
import com.myConsole.modules.sys.service.MyArticleLabelService;


@Service("myArticleLabelService")
public class MyArticleLabelServiceImpl extends ServiceImpl<MyArticleLabelDao, MyArticleLabelEntity> implements MyArticleLabelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MyArticleLabelEntity> page = this.page(
                new Query<MyArticleLabelEntity>().getPage(params),
                new QueryWrapper<MyArticleLabelEntity>()
        );

        return new PageUtils(page);
    }

}
