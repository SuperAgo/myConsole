package com.myConsole.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.modules.sys.entity.MyAboutEntity;

import java.util.Map;

/**
 * 关于
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-23 17:14:55
 */
public interface MyAboutService extends IService<MyAboutEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

