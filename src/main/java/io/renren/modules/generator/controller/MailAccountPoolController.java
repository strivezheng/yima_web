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

import io.renren.modules.generator.entity.MailAccountPoolEntity;
import io.renren.modules.generator.service.MailAccountPoolService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 邮箱表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@RestController
@RequestMapping("generator/mailaccountpool")
public class MailAccountPoolController {
    @Autowired
    private MailAccountPoolService mailAccountPoolService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:mailaccountpool:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mailAccountPoolService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:mailaccountpool:info")
    public R info(@PathVariable("id") Integer id){
		MailAccountPoolEntity mailAccountPool = mailAccountPoolService.getById(id);

        return R.ok().put("mailAccountPool", mailAccountPool);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:mailaccountpool:save")
    public R save(@RequestBody MailAccountPoolEntity mailAccountPool){
		mailAccountPoolService.save(mailAccountPool);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:mailaccountpool:update")
    public R update(@RequestBody MailAccountPoolEntity mailAccountPool){
		mailAccountPoolService.updateById(mailAccountPool);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:mailaccountpool:delete")
    public R delete(@RequestBody Integer[] ids){
		mailAccountPoolService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
