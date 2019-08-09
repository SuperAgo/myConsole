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

import com.myConsole.modules.sys.entity.MyLeavingMessageEntity;
import com.myConsole.modules.sys.service.MyLeavingMessageService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.R;



/**
 * 用户留言表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@RestController
@RequestMapping("sys/myleavingmessage")
public class MyLeavingMessageController {
    @Autowired
    private MyLeavingMessageService myLeavingMessageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:myleavingmessage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = myLeavingMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:myleavingmessage:info")
    public R info(@PathVariable("id") Integer id){
        MyLeavingMessageEntity myLeavingMessage = myLeavingMessageService.getById(id);

        return R.ok().put("myLeavingMessage", myLeavingMessage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:myleavingmessage:save")
    public R save(@RequestBody MyLeavingMessageEntity myLeavingMessage){
        myLeavingMessageService.save(myLeavingMessage);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:myleavingmessage:update")
    public R update(@RequestBody MyLeavingMessageEntity myLeavingMessage){
        ValidatorUtils.validateEntity(myLeavingMessage);
        myLeavingMessageService.updateById(myLeavingMessage);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:myleavingmessage:delete")
    public R delete(@RequestBody Integer[] ids){
        myLeavingMessageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
