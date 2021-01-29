package io.renren.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import io.renren.modules.app.entity.vo.AnswerItemVO;
import io.renren.modules.app.entity.vo.AnswerVO;
import lombok.Data;

/**
 * 同学录回答
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-01-17 11:03:42
 */
@Data
@TableName("txl_book_answer")
public class TxlBookAnswerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 同学录id
     */
    private Long bookId;
    /**
     * 同学录名称
     */
    private String bookName;
    /**
     * 回答者id
     */
    private Long answerUserId;
    /**
     * 回答者
     */
    private String answerUserName;
    /**
     * 详情json
     */
    private String answerDetail;



    /**
     * 状态：deleted：删除、normal：正常
     */
    private String status;

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
     * 回答
     */
    @TableField(exist = false)
    private AnswerVO answerVO;

}
