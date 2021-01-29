/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.entity.vo.LoginMiniProgramVo;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.app.form.LoginForm;

import java.util.Map;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserEntity queryByMobile(String mobile);

    /**
     * 用户登录
     * @param form    登录表单
     * @return 返回用户ID
     */
    long login(LoginForm form);

    Map<String, Object> loginMiniProgram(LoginMiniProgramVo loginVo);


    /**
     * 获取用户详情 -- 用户id
     * @param userId
     * @return
     */
    UserInfo getUserInfoByUserId(Long userId);


    /**
     * 获取用户详情 -- 手机号
     * @param mobile
     * @return
     */
    UserInfo queryUserInfoByMobile(String mobile);


    /**
     * 通过用户id更新数据
     *
     * @param userInfo
     * @return
     */
    boolean updateByUserId(UserInfo userInfo);

}
