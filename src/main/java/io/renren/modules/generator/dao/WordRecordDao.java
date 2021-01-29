package io.renren.modules.generator.dao;

import io.renren.modules.generator.entity.WordRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短词表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-18 15:45:27
 */
@Mapper
public interface WordRecordDao extends BaseMapper<WordRecordEntity> {
	
}
