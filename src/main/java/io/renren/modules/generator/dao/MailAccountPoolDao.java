package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.MailAccountPoolEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮箱表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@Mapper
public interface MailAccountPoolDao extends BaseMapper<MailAccountPoolEntity> {
	
}
