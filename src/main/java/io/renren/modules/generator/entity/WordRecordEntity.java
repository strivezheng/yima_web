package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 短词表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-18 15:45:27
 */
@Data
@TableName("word_record")
public class WordRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * uuid
	 */
	private String uuId;
	/**
	 * 明文
	 */
	private String openWord;
	/**
	 * 密文
	 */
	private String privateWord;
	/**
	 * 创建者
	 */
	private Integer userId;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
