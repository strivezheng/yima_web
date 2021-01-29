package io.renren.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import io.renren.modules.app.entity.vo.AnswerVO;
import io.renren.modules.app.entity.vo.BookInfoVO;
import io.renren.modules.app.entity.vo.RecentAnswerVO;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.generator.dao.TxlBookAnswerDao;
import io.renren.modules.generator.service.TxlBookAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.TxlClassmateBookDao;
import io.renren.modules.generator.entity.TxlClassmateBookEntity;
import io.renren.modules.generator.service.TxlClassmateBookService;


@Service("txlClassmateBookService")
public class TxlClassmateBookServiceImpl extends ServiceImpl<TxlClassmateBookDao, TxlClassmateBookEntity> implements TxlClassmateBookService {

    @Autowired
    private TxlBookAnswerDao txlBookAnswerDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TxlClassmateBookEntity> page = this.page(
                new Query<TxlClassmateBookEntity>().getPage(params),
                new QueryWrapper<TxlClassmateBookEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 为用户生成默认同学录
     *
     * @param userInfo
     * @return
     */
    public TxlClassmateBookEntity createUserDefaultBook(UserInfo userInfo) {

        Assert.notNull(userInfo, "用户信息不为空");

        TxlClassmateBookEntity bookEntity = null;
        bookEntity = getBookByUserId(userInfo.getUserId());

        if (bookEntity != null) {
            //存在，直接返回
            return bookEntity;
        }
        //添加
        bookEntity = new TxlClassmateBookEntity();
        bookEntity.setCreateTime(DateTime.now());

        bookEntity.setUserId(userInfo.getUserId());
        bookEntity.setUserName(userInfo.getNickName());
        bookEntity.setName(userInfo.getNickName() + "的同学录");

        try {
            //一个用户只有一本
            save(bookEntity);
        } catch (DuplicateKeyException e) {
            //并发插入
            bookEntity = getBookByUserId(userInfo.getUserId());
        }

        return bookEntity;

    }

    /**
     * 根据userId，获取该用户的同学录
     *
     * @param userId
     * @return
     */
    public TxlClassmateBookEntity getBookByUserId(Long userId) {
        Assert.notNull(userId, "请输入userId！");

        QueryWrapper<TxlClassmateBookEntity> wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);

        return getOne(wrapper);

    }

    /**
     * 根据userId，获取该用户的同学录
     *
     * @param userId
     * @return
     */
    public BookInfoVO getBookInfoByUserId(Long userId) {
        Assert.notNull(userId, "请输入userId！");

        QueryWrapper<TxlClassmateBookEntity> wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        TxlClassmateBookEntity bookEntity = getOne(wrapper);

        if (bookEntity == null) {
            return null;
        }

        BookInfoVO bookInfoVO = null;
        bookInfoVO = new BookInfoVO();
        BeanUtil.copyProperties(bookEntity, bookInfoVO);

        List<RecentAnswerVO> recentAnswerVOS = txlBookAnswerDao.selectPreAnswerAndTotal(bookInfoVO.getId());
        List<AnswerVO> answerVOList = json2List(recentAnswerVOS);
        bookInfoVO.setRecentAnswer(answerVOList);
        if (CollectionUtil.isEmpty(answerVOList)) {
            bookInfoVO.setAnswerCount(0);
        } else {
            bookInfoVO.setAnswerCount(recentAnswerVOS.get(0).getTotal());
        }

        return bookInfoVO;

    }

    /**
     * 根据bookId，获取同学录详细信息，最新五条回复，回复总数
     *
     * @param bookId
     * @return
     */
    public BookInfoVO getBookInfoByBookId(Long bookId) {
        Assert.notNull(bookId, "请输入userId！");

        QueryWrapper<TxlClassmateBookEntity> wrapper = new QueryWrapper();
        wrapper.eq("id", bookId);

        TxlClassmateBookEntity classmateBookEntity = getOne(wrapper);
        if (classmateBookEntity == null) {
            return null;
        }
        BookInfoVO bookInfoVO = new BookInfoVO();
        BeanUtil.copyProperties(classmateBookEntity, bookInfoVO);

        List<RecentAnswerVO> recentAnswerVOS = txlBookAnswerDao.selectPreAnswerAndTotal(bookInfoVO.getId());

        List<AnswerVO> answerVOList = json2List(recentAnswerVOS);

        bookInfoVO.setRecentAnswer(answerVOList);
        if (CollectionUtil.isEmpty(answerVOList)) {
            bookInfoVO.setAnswerCount(0);
        } else {
            bookInfoVO.setAnswerCount(recentAnswerVOS.get(0).getTotal());
        }


        return bookInfoVO;

    }

    /**
     * 将json转换成对象
     *
     * @param recentAnswerVOS
     * @return
     */
    private List<AnswerVO> json2List(List<RecentAnswerVO> recentAnswerVOS) {
        if (CollectionUtil.isEmpty(recentAnswerVOS)) {
            return new ArrayList<>();
        }
        List<AnswerVO> answerVOList = new ArrayList<>();
        for (RecentAnswerVO recentAnswerVO : recentAnswerVOS) {
            String json = recentAnswerVO.getAnswerDetail();
            AnswerVO answerVO = null;
            try {
                answerVO = JSONUtil.toBean(json, AnswerVO.class);
            } catch (Exception e) {

            }
            answerVOList.add(answerVO);
        }
        return answerVOList;


    }

}