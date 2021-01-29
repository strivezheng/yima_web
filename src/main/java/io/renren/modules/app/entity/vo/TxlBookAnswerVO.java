package io.renren.modules.app.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class TxlBookAnswerVO {

    /**
     * id
     */
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
     * 回答
     */
    private AnswerVO answerVO;

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


    ///////////////////////////////////////////////////////////////////////////
    // 参数示例
    ///////////////////////////////////////////////////////////////////////////

    /**
     *
     {

     "bookId": 1,
     "bookName": "my book",
     "answerUserId": 517,
     "answerUserName": "sevenn",
     "answerItemVOMap": {
     "1": {
     "question": "姓名",
     "answer": "seven",
     "type": "write"

     },
     "2": {
     "question": "破壳日",
     "answer": "1994-09-24",
     "type": "date"

     },
     "3": {
     "question": "梦想",
     "answer": "工程师",
     "type": "write"

     },
     "4": {
     "question": "电话",
     "answer": "18569468148",
     "type": "write"

     },
     "5": {
     "question": "对我的印象",
     "answer": "游戏控",
     "type": "select",
     "option":[
     "文艺","范二","帅哥","拉风","软妹子",
     "女汉子","手机控","起床困难户","购物狂",
     "技术宅","待解救","小清新","月光族","知识分子",
     "靠谱","游戏控"
     ]

     },
     "6": {
     "question": "最美好的回忆",
     "answer": "上课",
     "type": "write"

     },
     "7": {
     "question": "最疯狂的经历",
     "answer": "翻墙上网",
     "type": "write"

     },
     "8": {
     "question": "最猥琐的一面",
     "answer": "嘿嘿",
     "type": "write"

     },
     "9": {
     "question": "你想对我说的话",
     "answer": "加油，明天会更好",
     "type": "write"

     },

     "10": {
     "question": "附上你美美（帅帅）哒的照片吧",
     "answer": "",
     "type": "img",
     "imgList":[
     "https://upload.cc/i1/2020/01/17/jGLplM.jpeg",
     "https://upload.cc/i1/2020/01/17/jGLplM.jpeg",
     "https://upload.cc/i1/2020/01/17/jGLplM.jpeg"
     ]

     }
     },

     "type": "open"

     }
     */

}
