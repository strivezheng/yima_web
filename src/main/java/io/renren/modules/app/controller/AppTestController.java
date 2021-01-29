/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.app.controller;


import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.entity.vo.SubscriptionMessageVO;
import io.renren.modules.app.service.ApiWexinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * APP测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app/open")
@Api("APP测试接口")
public class AppTestController {

    @Autowired
    ApiWexinService apiWexinService;

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public R userInfo(@LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }


    @GetMapping("getQqAccessToken")
    public R getQqAccessToken(){
       String  result =  apiWexinService.getQqAccessToken();
        return R.ok(result);
    }

    @RequestMapping("/sendSubscriptionMessage")
    public R sendSubscriptionMessage(@RequestBody SubscriptionMessageVO messageVO){
       String  result =  apiWexinService.sendSubscriptionMessage(messageVO);
        return R.ok(result);
    }

}
