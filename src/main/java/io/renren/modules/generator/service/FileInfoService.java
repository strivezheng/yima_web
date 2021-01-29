package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.FileInfoEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 10:21:00
 */
public interface FileInfoService extends IService<FileInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

