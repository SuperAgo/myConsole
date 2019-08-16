package com.myConsole.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.myConsole.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myConsole.modules.sys.entity.MyLabelEntity;
import com.myConsole.modules.sys.service.MyLabelService;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.R;



/**
 * 文章分类标签
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@RestController
@RequestMapping("sys/mylabel")
public class MyLabelController {
    @Autowired
    private MyLabelService myLabelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:mylabel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = myLabelService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 全部label
     */
    @RequestMapping("/getAll")
    public JSONArray getAlllabel(){
        List<MyLabelEntity> myLabelList= myLabelService.getAlllabel();

        return JSONArray.parseArray(JSON.toJSONString(myLabelList));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:mylabel:info")
    public R info(@PathVariable("id") Integer id){
        MyLabelEntity myLabel = myLabelService.getById(id);

        return R.ok().put("myLabel", myLabel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:mylabel:save")
    public R save(@RequestBody MyLabelEntity myLabel){
        myLabelService.save(myLabel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:mylabel:update")
    public R update(@RequestBody MyLabelEntity myLabel){
        myLabelService.updateById(myLabel);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:mylabel:delete")
    public R delete(@RequestBody Integer[] ids){
        myLabelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/getSonLabelList")
    public R getSonLabelList(){
        List<Map> labelList = myLabelService.getSonLabelList();
        return R.ok().put("data",labelList);
    }

}
