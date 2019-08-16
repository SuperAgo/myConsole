package com.myConsole.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章分类标签
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Data
@TableName("my_label")
public class MyLabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String parentName;
	private Integer blogNum;
	/**
	 * Id
	 */
	@TableId
	private Integer id;
	/**
	 * 父Id(未0则为父标签)
	 */
	private Integer parentId;
	/**
	 * 标签等级：1标题栏，2文章类型
	 */
	private Integer tabGrade;
	/**
	 * 标签名称
	 */
	private String labelName;
	/**
	 * 简介
	 */
	private String remark;
	/**
	 * 图片背景
	 */
	private String background;
	/**
	 * 新增人
	 */
	private Integer insUser;
	/**
	 * 新增时间
	 */
	private Date insTime;
	/**
	 * 顺序
	 */
	private Integer sort;
	/**
	 * 地址
	 */
	private String url;
}
