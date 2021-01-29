package io.renren.modules.generator.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.generator.entity.ShowResEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@Mapper
public interface ShowResDao extends BaseMapper<ShowResEntity> {

    List<ShowResEntity> randomPage(Page page, @Param("platform") String platform,@Param("weight") String  weight);
	
}
