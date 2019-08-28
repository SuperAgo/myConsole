package com.myConsole.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;

import com.myConsole.modules.sys.dao.MyLeavingMessageDao;
import com.myConsole.modules.sys.entity.MyLeavingMessageEntity;
import com.myConsole.modules.sys.service.MyLeavingMessageService;

import javax.annotation.Resource;


@Service("myLeavingMessageService")
public class MyLeavingMessageServiceImpl extends ServiceImpl<MyLeavingMessageDao, MyLeavingMessageEntity> implements MyLeavingMessageService {

    @Resource
    private MyLeavingMessageDao myLeavingMessageDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MyLeavingMessageEntity> page = this.page(
                new Query<MyLeavingMessageEntity>().getPage(params),
                new QueryWrapper<MyLeavingMessageEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map getMyMessageBoard() {
        return myLeavingMessageDao.getMyMessageBoard();
    }

    @Override
    public int changeMessageBoards(Map myMessageBoard) {
        return myLeavingMessageDao.changeMessageBoards(myMessageBoard);
    }

}
