package io.renren.modules.app.controller;

import cn.hutool.json.JSONUtil;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.vo.AnswerVO;
import io.renren.modules.app.entity.vo.BookInfoVO;
import io.renren.modules.app.entity.vo.TxlBookAnswerVO;
import io.renren.modules.generator.entity.TxlBookAnswerEntity;
import io.renren.modules.generator.entity.TxlClassmateBookEntity;
import io.renren.modules.generator.service.TxlBookAnswerService;
import io.renren.modules.generator.service.TxlClassmateBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 同学录回答
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-11 16:05:21
 */
@RestController
@RequestMapping("appTxlBookAndAnswer/open")
public class AppTxlBookAndAnswerController {

    @Autowired
    private TxlBookAnswerService txlBookAnswerService;

    @Autowired
    private TxlClassmateBookService txlClassmateBookService;


    /**
     *  根据userId，获取该用户的同学录
     */
    @RequestMapping("/getBookInfoByUserId")
    public R getBookByUserId(@RequestParam Long userId) {
        BookInfoVO result = txlClassmateBookService.getBookInfoByUserId(userId);

        return R.ok().setData(result);
    }


    /**
     *  根据bookId，获取同学录详细信息，最新五条回复，回复总数
     */
    @RequestMapping("/getBookInfoByBookId")
    public R getBookInfoByBookId(@RequestParam Long bookId) {
        BookInfoVO result = txlClassmateBookService.getBookInfoByBookId(bookId);

        return R.ok().setData(result);
    }

    /**
     *  根据Id，获取回答
     */
    @RequestMapping("/getAnswerById")
    public R getAnswerById(@RequestParam Long answerId) {
        TxlBookAnswerEntity result = txlBookAnswerService.getById(answerId);
        if (result!= null){
            AnswerVO answerVO = JSONUtil.toBean(result.getAnswerDetail(), AnswerVO.class);
            result.setAnswerVO(answerVO);
        }

        return R.ok().setData(result);
    }



    /**
     * 根据同学录id，获取所有回答
     */
    @RequestMapping("/listAnswerByBookId")
    public R listAnswerByBookId(@RequestParam Long bookId) {
        List<TxlBookAnswerEntity> list = txlBookAnswerService.listByBookId(bookId);

        return R.ok().setData(list);
    }


    /**
     * 获取用户所有的回答
     */
    @RequestMapping("/listAnswerByAnswerUserId")
    public R listAnswerByAnswerUserId(@RequestParam Long answerUserId) {
        List<TxlBookAnswerEntity> list = txlBookAnswerService.listByAnswerUserId(answerUserId);

        return R.ok().setData(list);
    }



    /**
     * 新增或更新回答，根据有无id判断
     */
    @RequestMapping("/addOrUpdateAnswer")
    public R addOrUpdateAnswer(@RequestBody TxlBookAnswerVO bookAnswerVO) {
        boolean result = txlBookAnswerService.addOrUpdate(bookAnswerVO);

        return R.ok().setData(result);
    }



    /**
     * 根据id更新回答
     */
    @RequestMapping("/updateAnswerById")
    public R updateAnswerById(@RequestBody TxlBookAnswerEntity bookAnswerEntity) {
        boolean result = txlBookAnswerService.updateById(bookAnswerEntity);

        return R.ok().setData(result);
    }




}
