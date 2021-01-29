package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.YmRecordEntity;
import io.renren.modules.generator.service.YmRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * 姨妈记录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-11 16:05:21
 */
@RestController
@RequestMapping("generator/ymrecord")
public class YmRecordController {
    @Autowired
    private YmRecordService ymRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:ymrecord:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = ymRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:ymrecord:info")
    public R info(@PathVariable("id") Integer id) {
        YmRecordEntity ymRecord = ymRecordService.getById(id);

        return R.ok().put("ymRecord", ymRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:ymrecord:save")
    public R save(@RequestBody YmRecordEntity ymRecord) {
        ymRecordService.save(ymRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:ymrecord:update")
    public R update(@RequestBody YmRecordEntity ymRecord) {
        ymRecordService.updateById(ymRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:ymrecord:delete")
    public R delete(@RequestBody Integer[] ids) {
        ymRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }




}
