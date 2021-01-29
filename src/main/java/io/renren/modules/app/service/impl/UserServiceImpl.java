/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.constants.AppConstants;
import io.renren.common.constants.AppInfo;
import io.renren.common.constants.AppInfoConstants;
import io.renren.common.constants.YMConstants;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.SpringContextUtils;
import io.renren.common.validator.Assert;
import io.renren.modules.app.dao.UserDao;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.entity.vo.LoginMiniProgramVo;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.ApiWexinService;
import io.renren.modules.app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );


        return new PageUtils(page);
    }

    private ApiWexinService getWexinService() {
        return (ApiWexinService) SpringContextUtils.getBean("wexinService");
    }

    @Override
    public UserEntity queryByMobile(String mobile) {
        return (UserEntity) ((UserDao) this.baseMapper).selectOne((Wrapper) (new QueryWrapper()).eq("mobile", mobile));
    }

    /**
     * 获取用户详情 -- 用户id
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserEntity userEntity = getById(userId);
        cn.hutool.core.lang.Assert.notNull(userEntity, "该用户不存在！");

        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userEntity, userInfo);

        return userInfo;
    }


    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean updateByUserId(UserInfo userInfo) {
        UserEntity userEntity = new UserEntity();

        BeanUtil.copyProperties(userInfo, userEntity, CopyOptions.create().setIgnoreCase(true));

        userEntity.setUserId(userInfo.getUserId());


        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userInfo.getUserId());
        update(userEntity, updateWrapper);

        return true;


    }

    /**
     * 获取用户详情 -- 手机号
     *
     * @param mobile
     * @return
     */
    @Override
    public UserInfo queryUserInfoByMobile(String mobile) {
        UserEntity userEntity = queryByMobile(mobile);
        cn.hutool.core.lang.Assert.notNull(userEntity, "该用户不存在！");

        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userEntity, userInfo);
        return userInfo;
    }

    @Override
    public long login(LoginForm form) {
        UserEntity user = this.queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");
        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new RRException("手机号或密码错误");
        } else {
            return user.getUserId();
        }
    }

    private UserEntity getUserByWxMiniOpenId(String openId) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper();
        wrapper.eq("wx_mini_open_id", openId);
        UserEntity userEntity = (UserEntity) this.getOne(wrapper);
        return userEntity;
    }

    @Transactional
    @Override
    public Map<String, Object> loginMiniProgram(LoginMiniProgramVo loginVo) {
        log.info("小程序登录，参数：{}", loginVo);

        String openId;
        AppInfo appInfo = AppInfoConstants.getAppInfo(loginVo.getPlatform());
        if (AppInfoConstants.QQ.equals(appInfo.getPlatform())) {
            openId = (String) this.getWexinService().getQQMiniProgramOpenId(loginVo.getCode(), appInfo.getAppId(), appInfo.getSecret()).get("openId");
        } else {
            openId = (String) this.getWexinService().getMiniProgramOpenId(appInfo.getAppId(), appInfo.getSecret(), loginVo.getCode()).get("openId");
        }


        if (!StringUtils.hasText(openId)) {
            throw new RRException("小程序登录失败！");
        } else {
            UserEntity userEntity = this.getUserByWxMiniOpenId(openId);
            if (userEntity == null) {
                userEntity = new UserEntity();
                byte[] bytes = ("" + System.currentTimeMillis()).getBytes();
                String account = Base64Utils.encodeToString(bytes);
                userEntity.setNickName(loginVo.getNickName());
                userEntity.setWxMiniOpenId(openId);
                userEntity.setAvatar(loginVo.getAvatarUrl());
                userEntity.setCreateTime(new Date());
                userEntity.setSex(loginVo.getSex() + "");
                userEntity.setPlatform(appInfo.getClient());
                userEntity.setAvrgMenstruationDuration(YMConstants.DEFAULT_AVRG_YM_DURATION);


                boolean flag = false;
                try {
                    flag = this.save(userEntity);
                } catch (DuplicateKeyException e) {
                    log.info("小程序出现并发登录问题，参数：{}", loginVo);
                    userEntity = this.getUserByWxMiniOpenId(openId);
                }

            }

            Map<String, Object> map = new HashMap();
            map.put("openId", openId);
            map.put("account", userEntity);
            return map;
        }
    }


    public Dict subscribeMsg() {

        //1. 用户开通订阅
        //2. 用户允许推送

        return null;


    }

}
