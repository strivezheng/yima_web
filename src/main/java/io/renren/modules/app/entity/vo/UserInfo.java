package io.renren.modules.app.entity.vo;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.annotation.TableId;
import io.renren.modules.app.entity.UserEntity;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo   {

    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;

    private String wxMiniOpenId;
    private String nickName;
    private String userStatus;
    private String avatar;
    private String sex;
    private Integer weight;


    /**
     * 平均持续时间
     */
    private Integer avrgMenstruationDuration;
    /**
     * 平均间隔
     */
    private Integer avraMenstruationInterval;
    /**
     * 是否开启短信提醒
     */
    private String smsAlert;
    /**
     * 是否开启email提醒
     */
    private String emailAlert;
    /**
     * 提前提醒天数
     */
    private Integer alertFrontDays;
    /**
     * 当日提醒时间
     */
    private String  alertTime;
    /**
     * 邮箱
     */
    private String email;


    public void checkExist(){
        Assert.notNull(userId,"该用户不存在或已删除！");
    }

}
