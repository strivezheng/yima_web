package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 同学录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 11:03:41
 */
@Data
@TableName("txl_classmate_book")
public class TxlClassmateBookEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String des;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户
	 */
	private String userName;
	/**
	 * 类型：open 公开、private：私有
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
