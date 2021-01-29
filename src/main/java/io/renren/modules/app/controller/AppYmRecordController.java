package io.renren.modules.app.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import io.renren.common.constants.AppConstants;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.YmUtils;
import io.renren.modules.app.entity.vo.CalendarShowDataVo;
import io.renren.modules.generator.entity.YmRecordEntity;
import io.renren.modules.generator.service.YmRecordService;
import io.renren.modules.generator.service.YmRemarkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 姨妈记录
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-11 16:05:21
 */
@RestController
@RequestMapping("appYmrecord/open")
public class AppYmRecordController {
    @Autowired
    private YmRecordService ymRecordService;

    @Autowired
    private YmRemarkService ymRemarkService;


    /**
     * 根据userId获取记录
     */
    @RequestMapping("/getRecordByUserId")
    public R getRecordByUserId(@RequestParam Long userId) {
        List<YmRecordEntity> list = ymRecordService.getRecordByUserId(userId);

        return R.ok().setData(list);
    }


    /**
     * 添加一条记录
     */
    @RequestMapping("/ymComes")
    public R ymComes(@RequestParam Long userId, @RequestParam String beginDay) {

        Integer result = ymRecordService.ymComes(userId, DateUtil.parse(beginDay));

        return R.ok().setData(result);
    }


    /**
     * 姨妈结束
     */
    @RequestMapping("/ymGoes")
    public R ymGoes(@RequestParam Long userId, @RequestParam String dateDay) {
        boolean result = ymRecordService.ymGoes(userId, DateUtil.parse(dateDay));

        return R.ok().setData(result);
    }


    /**
     * 添加或编辑 根据当月有无记录，没有则新增，有则修改
     * <p>
     * 需要传 userId，开始时间，结束时间即可
     */
    @RequestMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody YmRecordEntity entity) {
        Dict result = ymRecordService.addOrUpdate(entity.getUserId(), entity);

        return R.ok().setData(result);
    }


    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/removeById")
    public R removeById(@RequestParam Integer id) {
        boolean result = ymRecordService.removeById(id);

        return R.ok().setData(result);
    }


    /**
     * 获取或动态计算当月信息
     *
     * @param userId
     * @param month  2022-03-09
     * @return
     */
    @RequestMapping("/generateForecastData")
    public R generateForecastData(@RequestParam Long userId, @RequestParam(required = false) String month) {
        if (StrUtil.isEmpty(month) || "null".equals(month) || "undefined".equals(month)) {
            month = DateUtil.format(new Date(), AppConstants.Date.DEFAULT_DAY_FORMAT);
        }
        CalendarShowDataVo result = ymRecordService.generateForecastData(userId, month);
        Dict dict = Dict.create()
                .set("data", result.assembleResult())
                .set("dateMonth", result.getDateMonth())
                .set("userId", result.getUserId())
                .set("remark", result.getRemark())
                .set("haveRealData", result.isHaveRealData());
        return R.ok().setData(dict);
    }


    /**
     * 获取备注
     *
     * @param userId
     * @param month  2022-03-09
     * @return
     */
    @RequestMapping("/getUserRemark")
    public R getUserRemark(@RequestParam Long userId, @RequestParam(required = false) String month) {
        Object result = ymRemarkService.getRecordFromCacheOrDb(userId, month);
        return R.ok().setData(result);
    }

    /**
     * 添加备注
     *
     * @param userId
     * @param monthDay 2022-03-09
     * @return
     */
    @RequestMapping("/addOrUpdateRemark")
    public R addOrUpdateRemark(Long userId, String monthDay, String remark) {
        monthDay = YmUtils.getBeginOfMonth(monthDay);
        boolean result = ymRemarkService.addOrUpdateRemark(userId, monthDay, remark);
        return R.ok().setData(result);
    }

    /**
     * 获取首页信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/homeInfo")
    public R homeInfo(Long userId) {
        Dict result = ymRecordService.homeInfo(userId);
        return R.ok().setData(result);
    }


}
