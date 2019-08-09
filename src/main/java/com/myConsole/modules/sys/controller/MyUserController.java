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

import com.myConsole.modules.sys.entity.MyUserEntity;
import com.myConsole.modules.sys.service.MyUserService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.R;



/**
 * 用户表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@RestController
@RequestMapping("sys/myuser")
public class MyUserController {
    @Autowired
    private MyUserService myUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:myuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = myUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:myuser:info")
    public R info(@PathVariable("id") Integer id){
        MyUserEntity myUser = myUserService.getById(id);

        return R.ok().put("myUser", myUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:myuser:save")
    public R save(@RequestBody MyUserEntity myUser){
        myUserService.save(myUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:myuser:update")
    public R update(@RequestBody MyUserEntity myUser){
        ValidatorUtils.validateEntity(myUser);
        myUserService.updateById(myUser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:myuser:delete")
    public R delete(@RequestBody Integer[] ids){
        myUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
