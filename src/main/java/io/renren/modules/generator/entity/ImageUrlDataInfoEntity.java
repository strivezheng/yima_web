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
 * @date 2019-09-07 10:21:00
 */
@Data
@TableName("image_url_data_info")
public class ImageUrlDataInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 文件id
	 */
	private Integer fileId;
	/**
	 * 地址
	 */
	private String url;
	/**
	 * 平台
	 */
	private Integer platform;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
