package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.generator.entity.BrowseRecordEntity;
import io.renren.modules.generator.service.BrowseRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 19:32:21
 */
@RestController
@RequestMapping("generator/browserecord")
public class BrowseRecordController {
    @Autowired
    private BrowseRecordService browseRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:browserecord:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = browseRecordService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 统计浏览量
     */
    @GetMapping("/open/statisticsUserVisitRecord")
    @ApiOperation("统计浏览量")
    public R list(@RequestParam(required = false) Integer userId,@RequestParam Map<String, Object> params) {
        PageUtils page = browseRecordService.statisticsUserVisitRecord(userId,params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:browserecord:info")
    public R info(@PathVariable("id") Integer id) {
        BrowseRecordEntity browseRecord = browseRecordService.getById(id);

        return R.ok().put("browseRecord", browseRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:browserecord:save")
    public R save(@RequestBody BrowseRecordEntity browseRecord) {
        browseRecordService.save(browseRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:browserecord:update")
    public R update(@RequestBody BrowseRecordEntity browseRecord) {
        browseRecordService.updateById(browseRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:browserecord:delete")
    public R delete(@RequestBody Integer[] ids) {
        browseRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
