package io.renren.modules.app.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginMiniProgramVo {
    @ApiModelProperty(
            value = "用户登录凭证",
            required = true
    )
    private String code;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户头像")
    private String avatarUrl;
    @ApiModelProperty("用户性别")
    private Integer sex;

    @ApiModelProperty("姨妈日历：qq、大姨妈日历：qq-dymrl、姨妈助手：qq-ymzs、姨妈神器：qq-ymsq")
    private String platform;

}
