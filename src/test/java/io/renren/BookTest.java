package io.renren;


import cn.hutool.core.util.RandomUtil;
import com.github.binarywang.java.emoji.EmojiConverter;
import io.renren.common.constants.YMConstants;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.entity.vo.UserInfo;
import io.renren.modules.app.service.UserService;
import io.renren.modules.generator.service.TxlClassmateBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookTest {

    @Autowired
    private TxlClassmateBookService classmateBookService;

    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

    @Autowired
    private UserService userService;


    /**
     * 生成用户的通讯录
     */
    @Test
    public void createUserDefaultBook() {

        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(6);
        userIdList.add(7);
        userIdList.add(8);
        userIdList.add(9);
        userIdList.add(10);
        userIdList.add(11);
        userIdList.add(12);
        userIdList.add(13);

        for (Integer integer : userIdList) {
            UserInfo userInfo =
            userService.getUserInfoByUserId(Long.valueOf(integer));

            classmateBookService.createUserDefaultBook(userInfo);
        }




    }

}
