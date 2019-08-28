/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.myConsole.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myConsole.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {

    List<SysOssEntity> findByIds(String ids);
}
