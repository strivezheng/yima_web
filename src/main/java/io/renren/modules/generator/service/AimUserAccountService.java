package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.AimUserAccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
public interface AimUserAccountService extends IService<AimUserAccountEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String importEmailAccount(List<AimUserAccountEntity> aimUserAccountList);

    Map sendTest(int userId);
}

