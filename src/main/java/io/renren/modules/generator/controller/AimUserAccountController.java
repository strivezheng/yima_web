package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.AimUserAccountEntity;
import io.renren.modules.generator.service.AimUserAccountService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@RestController
@RequestMapping("generator/aimuseraccount")
public class AimUserAccountController {
    @Autowired
    private AimUserAccountService aimUserAccountService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:aimuseraccount:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = aimUserAccountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:aimuseraccount:info")
    public R info(@PathVariable("id") Integer id){
		AimUserAccountEntity aimUserAccount = aimUserAccountService.getById(id);

        return R.ok().put("aimUserAccount", aimUserAccount);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:aimuseraccount:save")
    public R save(@RequestBody AimUserAccountEntity aimUserAccount){
		aimUserAccountService.save(aimUserAccount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:aimuseraccount:update")
    public R update(@RequestBody AimUserAccountEntity aimUserAccount){
		aimUserAccountService.updateById(aimUserAccount);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:aimuseraccount:delete")
    public R delete(@RequestBody Integer[] ids){
		aimUserAccountService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
