package io.renren.modules.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.cache.LocalCache;
import io.renren.common.constants.AppConstants;
import io.renren.common.utils.YmUtils;
import io.renren.modules.generator.entity.YmRecordEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.YmRemarkDao;
import io.renren.modules.generator.entity.YmRemarkEntity;
import io.renren.modules.generator.service.YmRemarkService;
import springfox.documentation.annotations.Cacheable;


@Service("ymRemarkService")
public class YmRemarkServiceImpl extends ServiceImpl<YmRemarkDao, YmRemarkEntity> implements YmRemarkService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<YmRemarkEntity> page = this.page(
                new Query<YmRemarkEntity>().getPage(params),
                new QueryWrapper<YmRemarkEntity>()
        );

        return new PageUtils(page);
    }


    public List<YmRemarkEntity> getRecordByUserId(Long userId) {

        QueryWrapper<YmRemarkEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        return list(queryWrapper);

    }


    /**
     * 从缓存或DB中查询数据
     *
     * @param userId
     * @param monthDay
     * @return
     */
    @Override
    public YmRemarkEntity getRecordFromCacheOrDb(Long userId, String monthDay) {

        String beginDayStr = YmUtils.getBeginOfMonth(monthDay);

        HashMap<String, YmRemarkEntity> ymRemarkEntityHashMap = LocalCache.USER_YM_REMARKS_MAP.get(userId);
        //缓存如果不存在，从数据库中查
        if (CollectionUtil.isEmpty(ymRemarkEntityHashMap) || ymRemarkEntityHashMap.get(beginDayStr) == null) {
            List<YmRemarkEntity> ymRemarkEntityList = getRecordByUserId(userId);

            ymRemarkEntityHashMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(ymRemarkEntityList)) {
                for (YmRemarkEntity entity : ymRemarkEntityList) {
                    ymRemarkEntityHashMap.put(DateUtil.format(entity.getDateMonth(), AppConstants.Date.DEFAULT_DAY_FORMAT), entity);
                }
            }
            LocalCache.USER_YM_REMARKS_MAP.put(userId, ymRemarkEntityHashMap);
        }

        YmRemarkEntity ymRemarkEntity = ymRemarkEntityHashMap.get(beginDayStr);
        return ymRemarkEntity;


    }


    /**
     * 添加备注
     *
     * @param userId
     * @param monthDay
     * @param remark
     * @return
     */
    public boolean addOrUpdateRemark(Long userId, String monthDay, String remark) {

        Assert.notNull(userId, "请选择用户id");
        Assert.notNull(monthDay, "请选择月份");

        Date monthDate = DateUtil.parseDate(monthDay);
        DateTime beginOfMonth = DateUtil.beginOfMonth(monthDate);
        String beginDayStr = beginOfMonth.toString(AppConstants.Date.DEFAULT_DAY_FORMAT);

        List<YmRemarkEntity> recordEntityList = getRecordByUserId(userId);

        YmRemarkEntity ymRemarkEntity = recordEntityList.stream()
                .filter(r -> beginDayStr.equals(DateUtil.format(r.getDateMonth(), AppConstants.Date.DEFAULT_DAY_FORMAT)))
                .findFirst()
                .orElse(null);

        if (ymRemarkEntity == null) {
            ymRemarkEntity = new YmRemarkEntity();
            ymRemarkEntity.setCreateTime(DateTime.now());
            ymRemarkEntity.setUpdateTime(DateTime.now());
            ymRemarkEntity.setRemark(remark);
            ymRemarkEntity.setUserId(userId);
            ymRemarkEntity.setDateMonth(DateUtil.parseDate(monthDay));

        } else {
            ymRemarkEntity.setRemark(remark);
        }

        boolean success = saveOrUpdate(ymRemarkEntity);
        if (success) {
            //保存成功，清除缓存
            LocalCache.USER_YM_REMARKS_MAP.put(userId, new HashMap<>());
        }

        return success;
    }

}