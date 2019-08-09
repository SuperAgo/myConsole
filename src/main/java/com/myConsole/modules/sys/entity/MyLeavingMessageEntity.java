package com.myConsole.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户留言表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Data
@TableName("my_leaving_message")
public class MyLeavingMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	@TableId
	private Integer id;
	/**
	 * 父Id（为0则为评论源）
	 */
	private Integer parentId;
	/**
	 * 博客Id，为空则为留言
	 */
	private Integer blogId;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 留言
	 */
	private String message;
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

}
