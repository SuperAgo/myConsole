package com.myConsole.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户博客表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Data
@TableName("my_blog")
public class MyBlogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<MyArticleLabelEntity> sonLabelList;
	private List<MyArticleLabelEntity> deleteSonLabelList;
	private List<Integer> addSonLabelList;
	private String contentHtml;
	/**
	 * Id
	 */
	@TableId
	private Integer id;
	/**
	 * banner图
	 */
	private String banner;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 主标签
	 */
	private Integer label;
	/**
	 * 主标签名称
	 */
	private String labelName;
	/**
	 * 作者名称
	 */
	private String nickName;
	/**
	 * 作者Id
	 */
	private Integer author;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 新增时间
	 */
	private Date insTime;
	/**
	 * 评论数量
	 */
	private Integer comments;
	/**
	 * 点赞数量
	 */
	private Integer compliments;
	/**
	 * 阅读量
	 */
	private Integer reader;
	/**
	 * 是否置顶(0非置顶，1置顶)
	 */
	private Integer isTop;
	/**
	 * 置顶时间
	 */
	private Date toppingTime;
	/**
	 * 是否精选(0非精选，1精选)
	 */
	private Integer isSelected;
	/**
	 * 精选时间
	 */
	private Date selectedTime;
	/**
	 * 状态：1可读，0已删除
	 */
	private Integer state;

}
