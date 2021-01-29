package io.renren.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.renren.common.constants.AppConstants;
import io.renren.modules.app.entity.vo.AnswerItemVO;
import io.renren.modules.app.entity.vo.AnswerVO;
import io.renren.modules.app.entity.vo.TxlBookAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.TxlBookAnswerDao;
import io.renren.modules.generator.entity.TxlBookAnswerEntity;
import io.renren.modules.generator.service.TxlBookAnswerService;


@Service("txlBookAnswerService")
@Slf4j
public class TxlBookAnswerServiceImpl extends ServiceImpl<TxlBookAnswerDao, TxlBookAnswerEntity> implements TxlBookAnswerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TxlBookAnswerEntity> page = this.page(
                new Query<TxlBookAnswerEntity>().getPage(params),
                new QueryWrapper<TxlBookAnswerEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据同学录id，获取所有回答
     *
     * @param bookId
     * @return
     */
    public List<TxlBookAnswerEntity> listByBookId(Long bookId) {

        Assert.notNull(bookId, "请输入同学录编号！");
        QueryWrapper<TxlBookAnswerEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("status", AppConstants.Status.STATUS_NORMAL);
        queryWrapper.orderBy(true, false, "create_time");

        List<TxlBookAnswerEntity> list = list(queryWrapper);

        for (TxlBookAnswerEntity entity : list) {
            try {
                AnswerVO answerVO = JSONUtil.toBean(entity.getAnswerDetail(), AnswerVO.class);
                entity.setAnswerVO(answerVO);
            } catch (Exception e) {
                log.error("answer转换异常：", e);
            }

        }

        return list;

    }

    /**
     * 获取用户所有的回答
     *
     * @param answerUserId
     * @return
     */
    public List<TxlBookAnswerEntity> listByAnswerUserId(Long answerUserId) {

        Assert.notNull(answerUserId, "请输入回答者id！");
        QueryWrapper<TxlBookAnswerEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("answer_user_id", answerUserId);
        queryWrapper.eq("status", AppConstants.Status.STATUS_NORMAL);
        queryWrapper.orderBy(true, false, "create_time");
        List<TxlBookAnswerEntity> list = list(queryWrapper);

        for (TxlBookAnswerEntity entity : list) {
            try {
                AnswerVO answerVO = JSONUtil.toBean(entity.getAnswerDetail(), AnswerVO.class);
                entity.setAnswerVO(answerVO);
            } catch (Exception e) {
                log.error("answer转换异常：", e);
            }

        }
        return list;

    }

    /**
     * 新增或更新，根据有无id判断
     *
     * @param bookAnswerVO
     * @return
     */
    public boolean addOrUpdate(TxlBookAnswerVO bookAnswerVO) {
        Assert.notNull(bookAnswerVO, "请输入参数！");
        Assert.notNull(bookAnswerVO.getBookId(), "请输入参数bookId！");
        Assert.notNull(bookAnswerVO.getBookName(), "请输入参数bookName！");
        Assert.notNull(bookAnswerVO.getAnswerUserId(), "请输入参数answerUserId！");
        Assert.notNull(bookAnswerVO.getAnswerUserName(), "请输入参数answerUserName！");
        Assert.notNull(bookAnswerVO.getAnswerVO(), "请输入参数answerVO！");


        TxlBookAnswerEntity bookAnswerEntity = new TxlBookAnswerEntity();
        BeanUtil.copyProperties(bookAnswerVO, bookAnswerEntity);
        //回答
        AnswerVO answerVO = bookAnswerVO.getAnswerVO();
        String jsonStr = JSONUtil.toJsonStr(answerVO);
        bookAnswerEntity.setAnswerDetail(jsonStr);

        if (bookAnswerEntity.getId() == null) {
            //新增
            bookAnswerEntity.setCreateTime(DateTime.now());
            bookAnswerEntity.setStatus(AppConstants.Status.STATUS_NORMAL);

            save(bookAnswerEntity);

        } else {
            //修改
            bookAnswerEntity.setUpdateTime(DateTime.now());

            updateById(bookAnswerEntity);
        }
        return true;

    }


}