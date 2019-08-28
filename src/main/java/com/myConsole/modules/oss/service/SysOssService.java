/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.myConsole.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.modules.oss.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
