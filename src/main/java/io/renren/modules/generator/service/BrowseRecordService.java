package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.BrowseRecordEntity;

import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
public interface BrowseRecordService extends IService<BrowseRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

    BrowseRecordEntity getRecord(Integer userId, Integer resId);


    /**
     * 获取用户浏览次数
     *
     * @param userId
     * @return map
     * userId
     * readTimes：总阅读次数（一条资源可能被看了多次）
     * resTimes：浏览资源条数
     */
    public Map getUserVisitTimes(Integer userId);

    /**
     * 统计浏览量
     * @param params：userId,isAsc
     * @return
     */
    public PageUtils statisticsUserVisitRecord(Integer userId,Map<String, Object> params);
}

