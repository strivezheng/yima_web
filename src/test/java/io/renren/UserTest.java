package io.renren;


import cn.hutool.core.util.RandomUtil;
import com.github.binarywang.java.emoji.EmojiConverter;
import io.renren.common.constants.YMConstants;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.entity.vo.LoginMiniProgramVo;
import io.renren.modules.app.service.UserService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

//    @Test
    public void contextLoads() {

        String emoji = "\uD83C\uDF2C";

        UserEntity userEntity = new UserEntity();
        userEntity.setNickName(emoji);
        userEntity.setWxMiniOpenId(RandomUtil.randomUUID());
        userEntity.setAvatar("12");
        userEntity.setCreateTime(new Date());
        userEntity.setSex( "");
        userEntity.setPlatform("");
        userEntity.setAvrgMenstruationDuration(YMConstants.DEFAULT_AVRG_YM_DURATION);


        boolean flag = userService.save(userEntity);

        System.out.println(flag);


    }

}
