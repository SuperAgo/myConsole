package com.myConsole.modules.sys.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.R;
import com.myConsole.modules.sys.entity.MyAboutEntity;
import com.myConsole.modules.sys.service.MyAboutService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 关于
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-23 17:14:55
 */
@RestController
@RequestMapping("sys/myabout")
public class MyAboutController {
    @Autowired
    private MyAboutService myAboutService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:myabout:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = myAboutService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:myabout:info")
    public R info(@PathVariable("id") Integer id){
        MyAboutEntity myAbout = myAboutService.getById(id);

        return R.ok().put("myAbout", myAbout);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:myabout:save")
    public R save(@RequestBody MyAboutEntity myAbout) throws UnsupportedEncodingException {
        myAbout.setContent(URLDecoder.decode(myAbout.getContent(), "utf-8"));
        myAbout.setInsTime(new Date());
        myAboutService.save(myAbout);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:myabout:update")
    public R update(@RequestBody MyAboutEntity myAbout){
        myAboutService.updateById(myAbout);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:myabout:delete")
    public R delete(@RequestBody Integer[] ids){
        myAboutService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
