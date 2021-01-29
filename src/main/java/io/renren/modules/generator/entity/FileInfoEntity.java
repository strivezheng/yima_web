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
@TableName("file_info")
public class FileInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer fileInfoId;
	/**
	 * 本地存储的文件名
	 */
	private String fileName;
	/**
	 * 文件格式
	 */
	private String fileFormat;
	/**
	 * 文件原始名称
	 */
	private String fileOriginalName;
	/**
	 * 主要地址
	 */
	private String fileUrl;
	/**
	 * 大小
	 */
	private Long fileSize;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
