package com.myConsole.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.modules.sys.entity.MyLeavingMessageEntity;

import java.util.Map;

/**
 * 用户留言表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
public interface MyLeavingMessageService extends IService<MyLeavingMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

