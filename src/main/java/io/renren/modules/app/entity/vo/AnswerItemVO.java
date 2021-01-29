package io.renren.modules.app.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class AnswerItemVO {

    /**
     * 问题
     */
    private String question;

    /**
     * 回答
     */
    private String answer;

    /**
     * 类型 write:填空、select：选择、img：图片、date：日期选择
     */
    private String type;

    /**
     * select类型的选项
     */
    private List<String> option;

    /**
     * img型数据
     */
    private List<String> imgList;


}
