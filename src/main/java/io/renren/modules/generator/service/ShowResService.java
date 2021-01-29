package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.ShowResEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
public interface ShowResService extends IService<ShowResEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils randomPage(Map<String, Object> params);


    /**
     * 修改权重
     * @param id
     * @param weight
     * @return
     */
    public boolean updateWeight(Integer id,Integer weight );
}

