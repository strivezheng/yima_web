package io.renren.modules.app.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserYmRecordVo {

    private Long userId;
    private String nickName;
    private String openId;
    /**
     * 平均间隔
     */
    private Integer avrgInterval;
    /**
     * 平均持续时长
     */
    private Integer avrgDuration;
    private Integer total;
    /**
     * 最后一次开始时间
     */
    private Date lastEndTime;
    /**
     * 最后一次结束时间
     */
    private Date lastStartTime;


}
