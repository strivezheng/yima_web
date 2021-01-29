package io.renren.common.constants;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


public class YMConstants {

    /**
     * 默认平均持续时间
     */
    public static final Integer DEFAULT_AVRG_YM_DURATION = 7;

    /**
     * 默认排卵平均持续时间
     */
    public static final Integer DEFAULT_ACRG_PL_DURATION = 10;


    /**
     * 默认姨妈平均间隔时间
     */
    public static final Integer DEFAULT_ACRG_YM_INTERVAL = 28;



}
