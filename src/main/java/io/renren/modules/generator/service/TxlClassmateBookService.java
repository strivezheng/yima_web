package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.vo.BookInfoVO;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.generator.entity.TxlClassmateBookEntity;

import java.util.List;
import java.util.Map;

/**
 * 同学录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 11:03:41
 */
public interface TxlClassmateBookService extends IService<TxlClassmateBookEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 为用户生成默认同学录
     *
     * @param userInfo
     * @return
     */
    TxlClassmateBookEntity createUserDefaultBook(UserInfo userInfo);


    /**
     * 根据userId，获取该用户的同学录
     *
     * @param userId
     * @return
     */
    TxlClassmateBookEntity getBookByUserId(Long userId);

    /**
     * 根据userId，获取该用户的同学录
     *
     * @param userId
     * @return
     */
    BookInfoVO getBookInfoByUserId(Long userId);


    /**
     * 根据bookId，获取同学录详细信息，最新五条回复，回复总数
     *
     * @param bookId
     * @return
     */
    public BookInfoVO getBookInfoByBookId(Long bookId);
}

