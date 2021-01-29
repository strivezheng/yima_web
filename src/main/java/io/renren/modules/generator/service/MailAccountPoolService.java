package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.MailAccountPoolEntity;

import java.util.Map;

/**
 * 邮箱表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
public interface MailAccountPoolService extends IService<MailAccountPoolEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

