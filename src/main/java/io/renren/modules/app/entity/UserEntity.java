/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 创建时间
	 */
	private Date createTime;

	private String wxMiniOpenId;
	private String nickName;
	private String userStatus;
	private String avatar;
	private String sex;
	private Integer weight;



	/**
	 * 平均持续时间
	 */
	private Integer avrgMenstruationDuration;
	/**
	 * 平均间隔
	 */
	private Integer avraMenstruationInterval;
	/**
	 * 是否开启短信提醒
	 */
	private String smsAlert;
	/**
	 * 是否开启email提醒
	 */
	private String emailAlert;
	/**
	 * 提前提醒天数
	 */
	private Integer alertFrontDays;
	/**
	 * 当日提醒时间
	 */
	private String  alertTime;
	/**
	 * 邮箱
	 */
	private String email;


}
