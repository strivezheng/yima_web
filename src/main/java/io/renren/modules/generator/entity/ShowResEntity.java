package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@Data
@TableName("show_res")
public class ShowResEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String url;
	/**
	 * 原网页地址
	 */
	private String pageUrl;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String avatar;
	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 具体来源
	 */
	private String shop;
	/**
	 * 
	 */
	private String goods;
	/**
	 * 资源类型：图片、视频
	 */
	private String resType;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 描述
	 */
	private String des;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 权重
	 */
	private Integer weight;

}
