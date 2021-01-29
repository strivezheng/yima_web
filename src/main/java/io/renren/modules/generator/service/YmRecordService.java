package io.renren.modules.generator.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.entity.vo.CalendarShowDataVo;
import io.renren.modules.generator.entity.YmRecordEntity;

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
public interface YmRecordService extends IService<YmRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 分页查询数据
     *
     * @param userId
     * @param params
     * @return
     */
    PageUtils queryUserRecord(Long userId, Map<String, Object> params);



    /**
     * 根据userId获取记录
     * @param userId
     * @return
     */
    List<YmRecordEntity> getRecordByUserId(Long userId);


    /**
     * 添加一条记录
     *
     * @param userId
     * @param beginDay
     * @return
     */
    Integer ymComes(Long userId, Date beginDay);


    /**
     * 姨妈结束
     *
     * @param userId
     * @param dateDay
     * @return
     */
    boolean ymGoes(Long userId, Date dateDay);



    /**
     * 添加或编辑 根据当月有无记录，没有则新增，有则修改
     * @param userId
     * @param ymRecordVo
     * @return
     */
    Dict addOrUpdate(Long userId, YmRecordEntity ymRecordVo);



    /**
     * 获取或动态计算当月信息
     * @param userId
     * @param month
     * @return
     */
    CalendarShowDataVo generateForecastData(Long userId, String month);





    /**
     * 获取首页信息
     * @param userId
     * @return
     */
    Dict homeInfo(Long userId);

}





