package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.YmRemarkEntity;

import java.util.Map;

/**
 * 姨妈备注
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-10 10:37:17
 */
public interface YmRemarkService extends IService<YmRemarkEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 从缓存或DB中查询数据
     *
     * @param userId
     * @param monthDay
     * @return
     */
    YmRemarkEntity getRecordFromCacheOrDb(Long userId, String monthDay);


    /**
     * 添加备注
     *
     * @param userId
     * @param monthDay
     * @param remark
     * @return
     */
    boolean addOrUpdateRemark(Long userId, String monthDay, String remark);
}

