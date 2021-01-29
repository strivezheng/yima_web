package io.renren.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.constants.AppConstants;
import io.renren.common.constants.YMConstants;
import lombok.Data;

import java.util.Date;

public class YmUtils {

    /**
     * 传入 某月的其中一天，计算当月的第一天
     *
     * @param monthDay yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getBeginOfMonth(String monthDay) {
        if (StrUtil.isEmpty(monthDay) || "null".equals(monthDay) || "undefined".equals(monthDay)) {
            monthDay = DateUtil.format(new Date(), AppConstants.Date.DEFAULT_DAY_FORMAT);
        }
        Date monthDate = DateUtil.parseDate(monthDay);
        DateTime beginOfMonth = DateUtil.beginOfMonth(monthDate);
        String beginDayStr = beginOfMonth.toString(AppConstants.Date.DEFAULT_DAY_FORMAT);

        return beginDayStr;
    }


    /**
     * 计算姨妈周期
     *
     * @param startDay
     * @return
     */
    public static YmData calculate(Date startDay) {
        return calculate(startDay, YMConstants.DEFAULT_AVRG_YM_DURATION, YMConstants.DEFAULT_ACRG_YM_INTERVAL);
    }

    /**
     * 计算姨妈周期
     *
     * @param startDay
     * @param intervalDay 姨妈从结束到下一次开始间隔时间
     * @param durationDay 姨妈从开始到结束的时间
     * @return
     */
    public static YmData calculate(Date startDay, Integer durationDay, Integer intervalDay) {

        if (null == intervalDay || intervalDay == 0) {
            intervalDay = YMConstants.DEFAULT_ACRG_YM_INTERVAL;
        }
        if (null == durationDay || durationDay == 0) {
            intervalDay = YMConstants.DEFAULT_AVRG_YM_DURATION;
        }
        DateTime startDateTime = DateTime.of(startDay);

        //1. 计算结束时间
        DateTime endDateTime = DateUtil.offsetDay(startDay, durationDay - 1);


//        //2. 计算上一次开始时间
//        DateTime preStartDateTime = DateUtil.offsetDay(startDay, -intervalDay);
//
//
//        //2. 计算上一次结束时间
//        DateTime preEndDateTime = DateUtil.offsetDay(preStartDateTime, YMConstants.DEFAULT_AVRG_YM_DURATION - 1);
//
//
        //2. 计算下一次开始时间
        DateTime nextStartDateTime = DateUtil.offsetDay(startDay, intervalDay);
//
//
//        //2. 计算下一次结束时间
//        DateTime nextEndDateTime = DateUtil.offsetDay(nextStartDateTime, YMConstants.DEFAULT_AVRG_YM_DURATION - 1);


        //3. 计算排卵开始时间
        DateTime plStartDateTime = DateUtil.offsetDay(nextStartDateTime, -9);

        //4. 计算排卵结束时间
        DateTime plEndDateTime = DateUtil.offsetDay(plStartDateTime, YMConstants.DEFAULT_ACRG_PL_DURATION - 1);

        Long ymPersist = DateUtil.betweenDay(startDay, endDateTime, true);

        YmData ymData = new YmData();
        ymData.setStartDateTime(startDateTime);
        ymData.setEndDateTime(endDateTime);
//        ymData.setPreStartDateTime(preStartDateTime);
//        ymData.setPreEndDateTime(preEndDateTime);
        ymData.setNextStartDateTime(nextStartDateTime);
//        ymData.setNextEndDateTime(nextEndDateTime);
        ymData.setPlStartDateTime(plStartDateTime);
        ymData.setPlEndDateTime(plEndDateTime);
        ymData.setYmPersist(Math.toIntExact(ymPersist));

        return ymData;
    }


    public static void main(String[] args) {
        YmData ymData = calculate(new Date());
        System.out.println(ymData.toString());
    }


    @Data
    public static class YmData {

        /**
         * ym开始时间
         */
        private DateTime startDateTime;

        /**
         * ym结束时间
         */
        private DateTime endDateTime;

        /**
         * 上一月 ym 开始时间
         */
        private DateTime preStartDateTime;


        /**
         * 上一月 ym 结束时间
         */
        private DateTime preEndDateTime;

        /**
         * 下一月 ym 开始时间
         */
        private DateTime nextStartDateTime;


        /**
         * 下一月 ym 结束时间
         */
        private DateTime nextEndDateTime;


        /**
         * 排卵开始时间
         */
        private DateTime plStartDateTime;

        /**
         * 排卵结束时间
         */
        private DateTime plEndDateTime;

        /**
         * 持续时间
         */
        private Integer ymPersist;

    }

}
