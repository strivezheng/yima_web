package io.renren.modules.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.constants.AppConstants;
import io.renren.common.constants.YMConstants;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.YmUtils;
import io.renren.modules.app.entity.vo.CalendarShowDataVo;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.app.entity.vo.UserYmRecordVo;
import io.renren.modules.app.service.UserService;
import io.renren.modules.generator.dao.YmRecordDao;
import io.renren.modules.generator.entity.YmRecordEntity;
import io.renren.modules.generator.service.YmRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("ymRecordService")
public class YmRecordServiceImpl extends ServiceImpl<YmRecordDao, YmRecordEntity> implements YmRecordService {

    @Autowired
    private UserService userService;

    @Autowired
    private YmRecordDao ymRecordDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<YmRecordEntity> page = this.page(
                new Query<YmRecordEntity>().getPage(params),
                new QueryWrapper<YmRecordEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 分页查询数据
     *
     * @param userId
     * @param params
     * @return
     */
    @Override
    public PageUtils queryUserRecord(Long userId, Map<String, Object> params) {

        QueryWrapper<YmRecordEntity> queryWrapper = new QueryWrapper<YmRecordEntity>();
        queryWrapper.eq("user_id", userId)
                .orderBy(true, true, "date_month");

        IPage<YmRecordEntity> page = this.page(
                new Query<YmRecordEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);

    }


    /**
     * 根据userId获取记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<YmRecordEntity> getRecordByUserId(Long userId) {

        QueryWrapper<YmRecordEntity> queryWrapper = new QueryWrapper<YmRecordEntity>();
        queryWrapper.eq("user_id", userId)
                .orderBy(true, true, "date_month");

        List<YmRecordEntity> list = list(queryWrapper);

        return list;
    }


    public List<YmRecordEntity> getRecordByUserIdAndMonth(Long userId, String month) {
        QueryWrapper<YmRecordEntity> queryWrapper = new QueryWrapper<YmRecordEntity>();
        queryWrapper.eq("user_id", userId)
                .eq("date_month", month);

        return list(queryWrapper);
    }

    /**
     * 添加一条记录
     *
     * @param userId
     * @param beginDay
     * @return
     */
    @Override
    public Integer ymComes(Long userId, Date beginDay) {
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        userInfo.checkExist();
        List<YmRecordEntity> recordEntityList = getRecordByUserId(userId);

        DateTime beginOfMonth = DateUtil.beginOfMonth(beginDay);

        YmRecordEntity ymRecordEntity = new YmRecordEntity();

        Date now = new Date();

        //平均间隔时间
        Integer intervalDay = userInfo.getAvraMenstruationInterval();
        if (intervalDay == null) {
            intervalDay = YMConstants.DEFAULT_ACRG_YM_INTERVAL;
        }

        YmUtils.YmData ymData = YmUtils.calculate(beginDay, userInfo.getAvrgMenstruationDuration(), intervalDay);

        ymRecordEntity.setUserId(userId);
        ymRecordEntity.setDateMonth(beginOfMonth.toJdkDate());
        ymRecordEntity.setYmStartTime(beginDay);
        ymRecordEntity.setYmEndTime(null);
        ymRecordEntity.setYmForecastStartTime(beginDay);
        ymRecordEntity.setYmForecastEndTime(ymData.getEndDateTime());
        ymRecordEntity.setPlStartTime(ymData.getPlStartDateTime());
        ymRecordEntity.setPlEndTime(ymData.getPlEndDateTime());
        ymRecordEntity.setCreateTime(now);
        ymRecordEntity.setUpdateTime(now);


        boolean success = saveOrUpdate(ymRecordEntity);
        return ymRecordEntity.getId();


    }

    /**
     * 姨妈结束
     *
     * @param userId
     * @param dateDay
     * @return
     */
    @Override
    public boolean ymGoes(Long userId, Date dateDay) {
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        userInfo.checkExist();

        List<YmRecordEntity> recordEntityList = getRecordByUserId(userId);
        Assert.notEmpty(recordEntityList, "不存在记录！");

        YmRecordEntity lastRecord = recordEntityList.stream()
                .filter(r -> r.getYmEndTime() == null) //没有结束的
                .sorted((o1, o2) -> o2.getYmStartTime().compareTo(o1.getYmStartTime())) //排序
                .findFirst()//最后一条记录
                .orElse(null);

        Assert.notNull(lastRecord, "不存在未结束记录！");

        Long day = DateUtil.between(lastRecord.getYmStartTime(), dateDay, DateUnit.DAY);

        lastRecord.setYmEndTime(dateDay);
        lastRecord.setYmPersist(Math.toIntExact(day));

        boolean success = updateById(lastRecord);


        return success;

    }


    /**
     * 添加或编辑 根据当月有无记录，没有则新增，有则修改
     *
     * @param userId
     * @param ymRecordVo
     * @return
     */
    @Override
    public Dict addOrUpdate(Long userId, YmRecordEntity ymRecordVo) {

        Date beginDay = ymRecordVo.getYmStartTime();
        Date endDay = ymRecordVo.getYmEndTime();
        Assert.notNull(beginDay, "请输入开始时间！");
        Assert.notNull(endDay, "请输入结束时间！");


        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        userInfo.checkExist();
//        List<YmRecordEntity> recordEntityList = getRecordByUserId(userId);


        DateTime beginOfMonth = DateUtil.beginOfMonth(beginDay);
        String beginDayStr = beginOfMonth.toString(AppConstants.Date.DEFAULT_DAY_FORMAT);


        YmRecordEntity ymRecordEntity = new YmRecordEntity();

        //计算持续时间
        Long day = DateUtil.between(beginDay, endDay, DateUnit.DAY);

        Date now = new Date();

        //平均间隔时间
        Integer intervalDay = userInfo.getAvraMenstruationInterval();
        if (intervalDay == null) {
            intervalDay = YMConstants.DEFAULT_ACRG_YM_INTERVAL;
        }

        YmUtils.YmData ymData = YmUtils.calculate(beginDay, userInfo.getAvrgMenstruationDuration(), intervalDay);
        ymRecordEntity.setId(ymRecordVo.getId());
        ymRecordEntity.setUserId(userId);
        ymRecordEntity.setDateMonth(beginOfMonth.toJdkDate());
        ymRecordEntity.setYmStartTime(beginDay);
        ymRecordEntity.setYmEndTime(endDay);
        ymRecordEntity.setYmForecastStartTime(beginDay);
        ymRecordEntity.setYmForecastEndTime(endDay);
        ymRecordEntity.setPlStartTime(ymData.getPlStartDateTime());
        ymRecordEntity.setPlEndTime(ymData.getPlEndDateTime());
        ymRecordEntity.setCreateTime(now);
        ymRecordEntity.setUpdateTime(now);
        ymRecordEntity.setYmPersist(Math.toIntExact(day));
        boolean success;
        try {

            success = saveOrUpdate(ymRecordEntity);
        } catch (DuplicateKeyException e) {
            throw new RRException("姨妈开始时间为：" + DateUtil.format(beginDay, AppConstants.Date.DEFAULT_DAY_FORMAT) + "的记录已存在！");
        }


        return Dict.create().set("success", success).set("data", ymRecordEntity);
    }


    /**
     * 获取或动态计算当月信息
     *
     * @param userId
     * @param month
     * @return
     */
    @Override
    public CalendarShowDataVo generateForecastData(Long userId, String month) {
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        userInfo.checkExist();

        Date monthDate = DateUtil.parseDate(month);
        //根据任意一天，获取当月的头一天 2019-12-01
        DateTime beginOfMonth = DateUtil.beginOfMonth(monthDate);
        String beginDayStr = beginOfMonth.toString(AppConstants.Date.DEFAULT_DAY_FORMAT);

        DateTime preBeginOfMonth = DateUtil.offsetMonth(beginOfMonth, -1);
        String preBeginDayStr = preBeginOfMonth.toString(AppConstants.Date.DEFAULT_DAY_FORMAT);

        //获取用户所有记录
        List<YmRecordEntity> recordEntityList = getRecordByUserId(userId);
        if (CollectionUtil.isEmpty(recordEntityList)) {
            //用户不存在记录，无法预测
            CalendarShowDataVo calendarShowDataVo = new CalendarShowDataVo();
            calendarShowDataVo.setUserId(userId);
            calendarShowDataVo.setDateMonth(beginOfMonth);
            return calendarShowDataVo;
        }

        //获取当月数据
        List<YmRecordEntity> currentDataList = recordEntityList.stream()
                .filter(r -> beginDayStr.equals(DateUtil.format(r.getDateMonth(), AppConstants.Date.DEFAULT_DAY_FORMAT)))
                .collect(Collectors.toList());

        List<YmRecordEntity> preDataList = recordEntityList.stream()
                .filter(r -> preBeginDayStr.equals(DateUtil.format(r.getDateMonth(), AppConstants.Date.DEFAULT_DAY_FORMAT)))
                .collect(Collectors.toList());


        CalendarShowDataVo calendarShowDataVo = new CalendarShowDataVo();
        calendarShowDataVo.setUserId(userId);
        calendarShowDataVo.setDateMonth(beginOfMonth);


        if (CollectionUtil.isNotEmpty(currentDataList)) {
            //前一个月有数据
            if (CollectionUtil.isNotEmpty(preDataList)) {
                for (YmRecordEntity entity : preDataList) {
                    //组装前端要的格式
                    assembleCalendarShow(calendarShowDataVo, entity);
                }
            }


            //当月存在数据
            for (YmRecordEntity entity : currentDataList) {
                //组装前端要的格式
                assembleCalendarShow(calendarShowDataVo, entity);
                calendarShowDataVo.setRemark(entity.getRemark());
                calendarShowDataVo.setHaveRealData(true);
            }


            return calendarShowDataVo;
        }

        //不存在，则计算
        Collections.reverse(recordEntityList);
        YmRecordEntity lastData = CollectionUtil.getFirst(recordEntityList);


        //最后一次的开始时间
        Date lastYmStartTime = lastData.getYmStartTime();
        // TODO: 2019/12/30 这里有问题，不应该是简单的月份平移，而应是每个人的周期乘以次数再平移过去
        Long offset = DateUtil.betweenMonth(lastYmStartTime, monthDate, true);
        if (monthDate.before(lastYmStartTime)) {
            offset = -offset;
        }
        //将时间算到你要计算的那月
        Date forecastStart = DateUtil.offset(lastYmStartTime, DateField.MONTH, Math.toIntExact(offset));

        DateTime now = new DateTime();
        YmUtils.YmData ymData = YmUtils.calculate(forecastStart, userInfo.getAvrgMenstruationDuration(), userInfo.getAvraMenstruationInterval());

        YmRecordEntity ymRecordEntity = new YmRecordEntity();
        setData(userId, beginOfMonth, ymRecordEntity, ymData);


        if (CollectionUtil.isNotEmpty(preDataList)) {
            for (YmRecordEntity entity : preDataList) {
                //组装前端要的格式
                assembleCalendarShow(calendarShowDataVo, entity);
            }
        } else {
            Long offset2 = offset - 1;
            //上一个月开始时间
            Date forecastPreStart = DateUtil.offset(lastYmStartTime, DateField.MONTH, Math.toIntExact(offset2));
            YmUtils.YmData ymData2 = YmUtils.calculate(forecastPreStart, userInfo.getAvrgMenstruationDuration(), userInfo.getAvraMenstruationInterval());

            YmRecordEntity preRecordEntity = new YmRecordEntity();
            setData(userId, beginOfMonth, preRecordEntity, ymData2);
            //组装前端要的格式
            assembleCalendarShow(calendarShowDataVo, preRecordEntity);

        }

        //计算前端要的格式
        assembleCalendarShow(calendarShowDataVo, ymRecordEntity);
        return calendarShowDataVo;
    }

    private void setData(Long userId, Date beginOfMonth, YmRecordEntity ymRecordEntity, YmUtils.YmData ymData) {
        ymRecordEntity.setUserId(userId);
        ymRecordEntity.setDateMonth(beginOfMonth);
        ymRecordEntity.setYmStartTime(ymData.getStartDateTime());
        ymRecordEntity.setYmEndTime(ymData.getEndDateTime());
        ymRecordEntity.setYmForecastStartTime(ymData.getStartDateTime());
        ymRecordEntity.setYmForecastEndTime(ymData.getEndDateTime());
        ymRecordEntity.setPlStartTime(ymData.getPlStartDateTime());
        ymRecordEntity.setPlEndTime(ymData.getPlEndDateTime());
        ymRecordEntity.setType(AppConstants.Type.TYPE_FORECAST);
        ymRecordEntity.setCreateTime(new Date());
        ymRecordEntity.setUpdateTime(new Date());
        ymRecordEntity.setYmPersist(ymData.getYmPersist());
    }

    /**
     * 组装前端需要的格式
     *
     * @param calendarShowDataVo
     * @param ymRecordEntity
     */
    private void assembleCalendarShow(CalendarShowDataVo calendarShowDataVo, YmRecordEntity ymRecordEntity) {

        Set<String> ymDays = calendarShowDataVo.getYmDays();
        Set<String> forecastYmDays = calendarShowDataVo.getForecastYmDays();
        Set<String> plDays = calendarShowDataVo.getPlDays();
        Set<Date> forecastYmDaysDate = calendarShowDataVo.getForecastYmDaysDate();

        ymDays = ymDays == null ? new HashSet<>() : ymDays;
        forecastYmDays = forecastYmDays == null ? new HashSet<>() : forecastYmDays;
        plDays = plDays == null ? new HashSet<>() : plDays;
        forecastYmDaysDate = forecastYmDaysDate == null ? new HashSet<>() : forecastYmDaysDate;

        List<DateTime> ymList = rangeDay(ymRecordEntity.getYmStartTime(), ymRecordEntity.getYmEndTime());
        List<DateTime> forecastYmList = rangeDay(ymRecordEntity.getYmForecastStartTime(), ymRecordEntity.getYmForecastEndTime());
        List<DateTime> plList = rangeDay(ymRecordEntity.getPlStartTime(), ymRecordEntity.getPlEndTime());

        ymDays.addAll(ymList.stream()
                .map(d -> DateUtil.format(d, AppConstants.Date.DEFAULT_DAY_FORMAT))
                .collect(Collectors.toSet())
        );

        forecastYmDays.addAll(forecastYmList.stream()
                .map(d -> DateUtil.format(d, AppConstants.Date.DEFAULT_DAY_FORMAT))
                .collect(Collectors.toSet())
        );

        plDays.addAll(plList.stream()
                .map(d -> DateUtil.format(d, AppConstants.Date.DEFAULT_DAY_FORMAT))
                .collect(Collectors.toSet())
        );

        if (AppConstants.Type.TYPE_FORECAST.equals(ymRecordEntity.getType())) {
            //预测数据
            ymDays.clear();
            //防止日期冲突, 以姨妈为准
            plDays.removeAll(forecastYmDays);
        } else {
            //实际数据
            //防止日期冲突, 以姨妈为准
            forecastYmDays.removeAll(ymDays);
            plDays.removeAll(ymDays);
        }


        calendarShowDataVo.setYmDays(ymDays);
        calendarShowDataVo.setForecastYmDays(forecastYmDays);
        calendarShowDataVo.setPlDays(plDays);

    }

    /**
     * 计算持续日期
     *
     * @param start
     * @param end
     * @return
     */
    private List<DateTime> rangeDay(Date start, Date end) {
        List<DateTime> dateTimeList = null;
        if (start != null && end != null) {
            dateTimeList = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR);
            return dateTimeList;
        }
        if (start != null) {
            dateTimeList = CollectionUtil.newArrayList(DateTime.of(start));
        }
        if (end != null) {
            dateTimeList = CollectionUtil.newArrayList(DateTime.of(end));
        }
        return dateTimeList;

    }

    /**
     * 获取首页信息
     *
     * @param userId
     * @return type:1:没有记录 2：未结束、3：预测下次开始
     */
    public Dict homeInfo(Long userId) {
        List<YmRecordEntity> recordEntityList = getRecordByUserId(userId);

        Dict result = Dict.create();

        if (CollectionUtil.isEmpty(recordEntityList)) {
            //用户没有记录
            result.set("showType", 1);
            return result;
        }

        UserInfo userInfo = userService.getUserInfoByUserId(userId);

        Collections.reverse(recordEntityList);
        YmRecordEntity lastData = CollectionUtil.getFirst(recordEntityList);

        if (lastData.getYmEndTime() == null) {
            //存在未结束记录，计算来了多长时间
            Long days = DateUtil.between(lastData.getYmStartTime(), new Date(), DateUnit.DAY);
            result.set("showType", 2);
            result.set("days", days + 1);
            result.set("ymStartTime", lastData.getYmStartTime());

        } else {
            //记录都已结束，预测下次时间

            Date monthDate = new Date();
            Date lastYmStartTime = lastData.getYmStartTime();
            Long offset = DateUtil.betweenMonth(lastYmStartTime, monthDate, true);
            if (monthDate.before(lastYmStartTime)) {
                offset = -offset;
            }
            Date forecastStart = DateUtil.offset(lastYmStartTime, DateField.MONTH, Math.toIntExact(offset));
            YmUtils.YmData ymData = YmUtils.calculate(forecastStart, userInfo.getAvrgMenstruationDuration(), userInfo.getAvraMenstruationInterval());
            Long days = DateUtil.between(new Date(), ymData.getNextStartDateTime(), DateUnit.DAY);
            result.set("showType", 3);
            result.set("days", days + 1);
            result.set("ymStartTime", ymData.getNextStartDateTime());

        }

        return result;

    }


    /**
     * 获取未来几天将要来姨妈的用户
     *
     * @param days
     * @return
     */
    public Dict getWillComesUser(int days) {

        List<UserYmRecordVo> lastRecordList = ymRecordDao.getUserLastRecord();

        DateTime now = DateTime.now();
        String month = now.toString(AppConstants.Date.DEFAULT_DAY_FORMAT);

        for (UserYmRecordVo userYmRecordVo : lastRecordList) {

            Date startTime = userYmRecordVo.getLastStartTime();
            Date endTime = userYmRecordVo.getLastEndTime();

            //平均间隔
            Integer avrgInterval = userYmRecordVo.getAvrgInterval();

            //平均持续时长
            Integer avrgDuration = userYmRecordVo.getAvrgDuration();
            avrgInterval = avrgInterval == null ? YMConstants.DEFAULT_ACRG_YM_INTERVAL : avrgInterval;
            avrgDuration = avrgDuration == null ? YMConstants.DEFAULT_AVRG_YM_DURATION : avrgDuration;

//            if (endTime != null) {
//
//                YmUtils.
//
//            }
            // TODO: 2019/12/31 未完成 
            CalendarShowDataVo calendarShowDataVo = generateForecastData(userYmRecordVo.getUserId(), month);


        }


        return null;
    }


}