package io.renren.modules.generator.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.generator.entity.BrowseRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@Mapper
public interface BrowseRecordDao extends BaseMapper<BrowseRecordEntity> {

    Map getUserVisitTimes(@Param("userId") Integer userId);

    List<Map> statisticsUserVisitRecord(IPage page, @Param("userId") Integer userId);

	
}
