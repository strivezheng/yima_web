package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.WordRecordEntity;

import java.util.Map;

/**
 * 短词表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-05-18 15:45:27
 */
public interface WordRecordService extends IService<WordRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

