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

import com.myConsole.modules.sys.entity.MyArticleLabelEntity;
import com.myConsole.modules.sys.service.MyArticleLabelService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@RestController
@RequestMapping("sys/myarticlelabel")
public class MyArticleLabelController {
    @Autowired
    private MyArticleLabelService myArticleLabelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:myarticlelabel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = myArticleLabelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:myarticlelabel:info")
    public R info(@PathVariable("id") Integer id){
        MyArticleLabelEntity myArticleLabel = myArticleLabelService.getById(id);

        return R.ok().put("myArticleLabel", myArticleLabel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:myarticlelabel:save")
    public R save(@RequestBody MyArticleLabelEntity myArticleLabel){
        myArticleLabelService.save(myArticleLabel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:myarticlelabel:update")
    public R update(@RequestBody MyArticleLabelEntity myArticleLabel){
        ValidatorUtils.validateEntity(myArticleLabel);
        myArticleLabelService.updateById(myArticleLabel);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:myarticlelabel:delete")
    public R delete(@RequestBody Integer[] ids){
        myArticleLabelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
