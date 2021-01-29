package io.renren.modules.generator.dao;

import io.renren.modules.app.entity.vo.UserYmRecordVo;
import io.renren.modules.generator.entity.YmRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 姨妈记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-11 16:05:21
 */
@Mapper
public interface YmRecordDao extends BaseMapper<YmRecordEntity> {

    List<UserYmRecordVo> getUserLastRecord();
	
}
