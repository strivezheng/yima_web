package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 姨妈备注
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-10 10:37:17
 */
@Data
@TableName("ym_remark")
public class YmRemarkEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 月份
	 */
	private Date dateMonth;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
