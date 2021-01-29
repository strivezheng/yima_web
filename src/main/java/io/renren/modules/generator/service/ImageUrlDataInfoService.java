package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.ImageUrlDataInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 10:21:00
 */
public interface ImageUrlDataInfoService extends IService<ImageUrlDataInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据fileId，查询图片链接信息
     * @param fileId
     * @return
     */
    List<ImageUrlDataInfoEntity> getImageUrlInfoByFileId(int fileId);

}

