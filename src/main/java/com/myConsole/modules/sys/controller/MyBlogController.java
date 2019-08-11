package com.myConsole.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.myConsole.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myConsole.modules.sys.entity.MyBlogEntity;
import com.myConsole.modules.sys.service.MyBlogService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.R;


/**
 * 用户博客表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@RestController
@RequestMapping("sys/myblog")
public class MyBlogController {
    @Autowired
    private MyBlogService myBlogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:myblog:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = myBlogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:myblog:info")
    public R info(@PathVariable("id") Integer id) {
        MyBlogEntity myBlog = myBlogService.selectById(id);
        return R.ok().put("myBlog", myBlog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:myblog:save")
    public R save(@RequestBody MyBlogEntity myBlog) {
        int i = myBlogService.insertSelective(myBlog);
        if (i < 1) {
            return R.error();
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:myblog:update")
    public R update(@RequestBody MyBlogEntity myBlog) {
        int i = myBlogService.updateByPrimaryKeySelective(myBlog);
        if (i < 1) {
            return R.error();
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:myblog:delete")
    public R delete(@RequestBody Integer[] ids) {
        myBlogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
