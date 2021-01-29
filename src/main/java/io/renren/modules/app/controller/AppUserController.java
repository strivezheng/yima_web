/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.entity.vo.LoginMiniProgramVo;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.app.form.LoginForm;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/appUser/open")
@Api("APP登录接口")
public class AppUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 获取用户详情 -- 用户id
     */
    @RequestMapping("/getUserInfoByUserId")
    public R getUserInfoByUserId(@RequestParam Long userId){
        UserInfo result = userService.getUserInfoByUserId(userId);

        return R.ok().setData(result);
    }

    /**
     * 获取用户详情 -- 用户id
     */
    @RequestMapping("/updateByUserId")
    public R updateByUserId(@RequestBody UserInfo userInfo){
        boolean result = userService.updateByUserId(userInfo);

        return R.ok().setData(result);
    }

}
