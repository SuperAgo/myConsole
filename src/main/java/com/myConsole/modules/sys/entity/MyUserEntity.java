package com.myConsole.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-07 16:18:28
 */
@Data
@TableName("my_user")
public class MyUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	@TableId
	private Integer id;
	/**
	 * 真实姓名
	 */
	private String userName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 手机号
	 */
	private Integer telephone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 网络头像
	 */
	private String networkAvatar;
	/**
	 * 1男
            2女
	 */
	private Integer sex;
	/**
	 * 个人签名
	 */
	private String signature;
	/**
	 * 简介
	 */
	private String introduction;
	/**
	 * 爱好
	 */
	private String hobby;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 地区代码
	 */
	private String regionCode;
	/**
	 * 手机号
	 */
	private String background;
	/**
	 * 小图1
	 */
	private String picOne;
	/**
	 * 小图2
	 */
	private String picTwo;
	/**
	 * 小图3
	 */
	private String picThree;
	/**
	 * 小图4
	 */
	private String picFour;
	/**
	 * 留言板背景图
	 */
	private String messageBoardPictures;
	/**
	 * 留言板寄语
	 */
	private String messageBoards;
	/**
	 * 状态
            1使用中
            0已删除
	 */
	private Integer state;
	/**
	 * 0未实名
            1已实名
	 */
	private Integer struts;
	/**
	 * 新增时间
	 */
	private Date insTime;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 1.博主
            2.其他
	 */
	private Integer type;
	/**
	 * 订阅状态:0未订阅，1已订阅
	 */
	private Integer subscribe;

}
