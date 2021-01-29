package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 邮箱表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@Data
@TableName("mail_account_pool")
public class MailAccountPoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 所属用户
	 */
	private Integer userId;
	/**
	 * 账户
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 授权码
	 */
	private String code;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 默认权重
	 */
	private Integer defaultWeight;
	/**
	 * 健康值
	 */
	private Integer health;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 短信平台
	 */
	private String smsPlatform;
	/**
	 * 
	 */
	private String phoneArea;
	/**
	 * 
	 */
	private String header;

}
