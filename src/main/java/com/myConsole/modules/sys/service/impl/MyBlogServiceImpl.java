package com.myConsole.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myConsole.modules.sys.dao.MyArticleLabelDao;
import com.myConsole.modules.sys.entity.MyArticleLabelEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myConsole.common.utils.PageUtils;
import com.myConsole.common.utils.Query;

import com.myConsole.modules.sys.dao.MyBlogDao;
import com.myConsole.modules.sys.entity.MyBlogEntity;
import com.myConsole.modules.sys.service.MyBlogService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;


@Service("myBlogService")
public class MyBlogServiceImpl extends ServiceImpl<MyBlogDao, MyBlogEntity> implements MyBlogService {
    @Resource
    private MyBlogDao myBlogDao;
    @Resource
    private MyArticleLabelDao myArticleLabelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("state", 1);
        IPage<MyBlogEntity> page = myBlogDao.page(
                new Query<MyBlogEntity>().getPage(params),
                params
        );
        return new PageUtils(page);
    }

    @Override
    public MyBlogEntity selectById(Integer id) {
        MyBlogEntity myBlogEntity = myBlogDao.selectById(id);
        List<MyArticleLabelEntity> sonLabelList = myArticleLabelDao.selectByBlogId(id);
        myBlogEntity.setSonLabelList(sonLabelList);
        return myBlogEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateByPrimaryKeySelective(MyBlogEntity myBlog) {
        int back = 1;
        try {
            myBlog.setContent(URLDecoder.decode(myBlog.getContent(), "utf-8"));
            int i = myBlogDao.updateByPrimaryKeySelective(myBlog);
            List<Integer> addSonLabelList = myBlog.getAddSonLabelList();
            List<MyArticleLabelEntity> deleteSonLabelList = myBlog.getDeleteSonLabelList();
            int add = 0;
            int del = 0;
            if (addSonLabelList.size() > 0) {
                MyArticleLabelEntity myArticleLabelEntity = null;
                for (Integer x : addSonLabelList) {
                    myArticleLabelEntity = new MyArticleLabelEntity();
                    myArticleLabelEntity.setBlogId(myBlog.getId());
                    myArticleLabelEntity.setLabelId(x);
                    add += myArticleLabelDao.insert(myArticleLabelEntity);
                }
            }
            if (deleteSonLabelList.size() > 0) {
                for (MyArticleLabelEntity deMyArticleLabelEntity : deleteSonLabelList) {
                    del += myArticleLabelDao.deleteById(deMyArticleLabelEntity.getId());
                }
            }
            if ((i + add + del) < (1 + addSonLabelList.size() + deleteSonLabelList.size())) {
                back = 0;
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (back < 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return back;
    }

    @Override
    public int insertSelective(MyBlogEntity myBlog) {
        try {
            myBlog.setContent(URLDecoder.decode(myBlog.getContent(), "utf-8"));
            if (1 == myBlog.getIsTop()) {
                myBlog.setToppingTime(new Date());
            }
            if (1 == myBlog.getIsSelected()) {
                myBlog.setSelectedTime(new Date());
            }
            myBlog.setAuthor(1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return myBlogDao.insertSelective(myBlog);
    }

    @Override
    public int setTop(Integer id) {
        MyBlogEntity myBlogEntity = new MyBlogEntity();
        myBlogEntity.setId(id);
        myBlogEntity.setIsTop(1);
        myBlogEntity.setToppingTime(new Date());
        return myBlogDao.updateByPrimaryKeySelective(myBlogEntity);
    }

    @Override
    public int setSelected(Integer id) {
        MyBlogEntity myBlogEntity = new MyBlogEntity();
        myBlogEntity.setId(id);
        myBlogEntity.setIsSelected(1);
        myBlogEntity.setSelectedTime(new Date());
        return myBlogDao.updateByPrimaryKeySelective(myBlogEntity);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        int i = myBlogDao.deleteByIds(ids);
        if (i < ids.size()) {
            return 0;
        }
        return 1;
    }
}
