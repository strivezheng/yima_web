package io.renren.modules.app.entity.vo;

import cn.hutool.core.lang.Dict;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Data
public class CalendarShowDataVo {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 所属年月日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateMonth;

    private  Set<String> ymDays;


    private Set<String> forecastYmDays;
    private Set<Date> forecastYmDaysDate;

    private  Set<String> plDays;

    private String remark;
    private boolean haveRealData;

    /**
     * {
     * date: '2019-11-1',
     * type: 1,
     * data: {
     * custom: '自定义信息',
     * name: '自定义消息头'
     * }
     * }
     *
     * @return
     */
    public List<Dict> assembleResult() {

        // type ：1 排卵期，2：预测期，3：来临期
        List<Dict> resultList = new ArrayList<>();
        Dict vo = null;
        ymDays = ymDays == null ? new HashSet<>() : ymDays;
        forecastYmDays = forecastYmDays == null ? new HashSet<>() : forecastYmDays;
        plDays = plDays == null ? new HashSet<>() : plDays;
        for (String plDay : plDays) {
            vo = Dict.create()
                    .set("date", plDay)
                    .set("type", 1)
                    .set("data", null);
            resultList.add(vo);
        }
        for (String date : forecastYmDays) {
            vo = Dict.create()
                    .set("date", date)
                    .set("type", 2)
                    .set("data", null);
            resultList.add(vo);
        }
        for (String date : ymDays) {
            vo = Dict.create()
                    .set("date", date)
                    .set("type", 3)
                    .set("data", null);
            resultList.add(vo);
        }
        return resultList;
    }

}
