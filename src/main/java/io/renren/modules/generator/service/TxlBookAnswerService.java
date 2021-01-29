package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.vo.TxlBookAnswerVO;
import io.renren.modules.generator.entity.TxlBookAnswerEntity;

import java.util.List;
import java.util.Map;

/**
 * 同学录回答
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 11:03:42
 */
public interface TxlBookAnswerService extends IService<TxlBookAnswerEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 根据同学录id，获取所有回答
     * @param bookId
     * @return
     */
    List<TxlBookAnswerEntity> listByBookId(Long bookId);

    /**
     * 获取用户所有的回答
     * @param answerUserId
     * @return
     */
    List<TxlBookAnswerEntity> listByAnswerUserId(Long answerUserId);



    /**
     * 新增或更新，根据有无id判断
     * @param bookAnswerVO
     * @return
     */
    boolean addOrUpdate(TxlBookAnswerVO bookAnswerVO);


}

