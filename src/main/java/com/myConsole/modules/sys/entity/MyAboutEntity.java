package com.myConsole.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关于
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-23 17:14:55
 */
@Data
@TableName("my_about")
public class MyAboutEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	@TableId
	private Integer id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 时间
	 */
	private Date insTime;

}
