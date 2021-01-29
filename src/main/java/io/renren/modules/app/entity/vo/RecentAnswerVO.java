package io.renren.modules.app.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RecentAnswerVO {


    private Integer id;
    private Integer bookId;
    private Integer answerUserId;
    private String bookName;
    private String answerUserName;
    private String answerDetail;
    private String type;
    private String status;
    private Date createTime;


    /**
     * 回答总条数
     */
    private Integer total;

}
