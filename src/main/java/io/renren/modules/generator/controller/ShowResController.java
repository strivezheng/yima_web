package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.generator.entity.ShowResEntity;
import io.renren.modules.generator.service.ShowResService;
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
@RequestMapping("generator/showres")
public class ShowResController {
    @Autowired
    private ShowResService showResService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:showres:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = showResService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:showres:info")
    public R info(@PathVariable("id") Integer id){
		ShowResEntity showRes = showResService.getById(id);

        return R.ok().put("showRes", showRes);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:showres:save")
    public R save(@RequestBody ShowResEntity showRes){
		showResService.save(showRes);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:showres:update")
    public R update(@RequestBody ShowResEntity showRes){
		showResService.updateById(showRes);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:showres:delete")
    public R delete(@RequestBody Integer[] ids){
		showResService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }






}
