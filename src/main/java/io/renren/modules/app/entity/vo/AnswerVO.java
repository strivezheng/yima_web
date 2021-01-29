package io.renren.modules.app.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class AnswerVO {



    /**
     * 名字
     */
    private String myName;
    /**
     * 梦想
     */
    private String myDream;
    /**
     * 联系方式
     */
    private String myPhone;
    /**
     * 生日
     */
    private String myBirthdaty;
    /**
     * 最好的会议
     */
    private String bestMemory;
    /**
     * 最好的经历
     */
    private String bestExperience;
    /**
     * 最猥琐的一面
     */
    private String bestBad;
    /**
     * 对我说
     */
    private String talk2me;
    /**
     * 选择的印象
     */
    private List<String> impressionOptionsChooses;
    /**
     * 印象数据
     */
    private List<String> impressionDatas;
    /**
     * 图片集合
     */
    private List<String> imageList;


//    /**
//     * 回答总条数
//     */
//    private Integer total;

}
