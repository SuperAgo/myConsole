package com.myConsole.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.modules.sys.entity.MyLabelEntity;

import java.util.Map;

/**
 * 文章分类标签
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
public interface MyLabelService extends IService<MyLabelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

