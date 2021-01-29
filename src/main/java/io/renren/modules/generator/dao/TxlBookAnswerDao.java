package io.renren.modules.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.app.entity.vo.RecentAnswerVO;
import io.renren.modules.generator.entity.TxlBookAnswerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 同学录回答
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 11:03:42
 */
@Mapper
public interface TxlBookAnswerDao extends BaseMapper<TxlBookAnswerEntity> {

    /**
     * 获取前几条回答，以及回答总数
     * @param bookId
     * @return
     */
    List<RecentAnswerVO> selectPreAnswerAndTotal(Integer bookId);
	
}
