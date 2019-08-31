package com.myConsole.modules.sys.service.impl;

import cn.hutool.extra.mail.MailUtil;
import com.myConsole.modules.sys.dao.MyArticleLabelDao;
import com.myConsole.modules.sys.dao.MyLabelDao;
import com.myConsole.modules.sys.dao.MyUserDao;
import com.myConsole.modules.sys.entity.LabelGradeEnum;
import com.myConsole.modules.sys.entity.MyArticleLabelEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

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
    @Resource
    private MyLabelDao myLabelDao;
    @Resource
    private MyUserDao myUserDao;

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
        int i = 0;
        try {
            myBlog.setContent(URLDecoder.decode(myBlog.getContent(), "utf-8"));
            myBlog.setContentHtml(URLDecoder.decode(myBlog.getContentHtml(), "utf-8"));
            if (null != myBlog.getIsTop() && 1 == myBlog.getIsTop()) {
                myBlog.setToppingTime(new Date());
            }
            if (null != myBlog.getIsSelected() && 1 == myBlog.getIsSelected()) {
                myBlog.setSelectedTime(new Date());
            }
            myBlog.setAuthor(1);
            myBlog.setInsTime(new Date());
            return myBlogDao.insertSelective(myBlog);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            sendEmail(myBlog);
        }
        return i;
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

    private void sendEmail(MyBlogEntity myBlog) {
        ArrayList<String> tos = myUserDao.queryAllSubscribedMailboxes();
        StringBuilder contentHtml=new StringBuilder();
        contentHtml.append("<div><img src="+myBlog.getBanner()+" style='max-width: 100%;height: auto;'></div>");
        contentHtml.append(myBlog.getContentHtml());
        contentHtml.append("<a href='http://www.super100wj.top:8080/free/blog'>更多</a>");
        MailUtil.send(tos, myBlog.getTitle(),contentHtml.toString() , true);
    }
}
