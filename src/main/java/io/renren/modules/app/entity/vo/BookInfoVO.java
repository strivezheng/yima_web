package io.renren.modules.app.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class BookInfoVO {


    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String des;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户
     */
    private String userName;
    /**
     * 类型：open 公开、private：私有
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 回答总数
     */
    private int answerCount;

    /**
     * 最近的几条回答
     */
    private List<AnswerVO> recentAnswer;


}
