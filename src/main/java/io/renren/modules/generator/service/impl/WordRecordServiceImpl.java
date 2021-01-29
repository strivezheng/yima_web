package io.renren.modules.generator.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.UUID;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.WordRecordDao;
import io.renren.modules.generator.entity.WordRecordEntity;
import io.renren.modules.generator.service.WordRecordService;


@Service("wordRecordService")
public class WordRecordServiceImpl extends ServiceImpl<WordRecordDao, WordRecordEntity> implements WordRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WordRecordEntity> page = this.page(
                new Query<WordRecordEntity>().getPage(params),
                new QueryWrapper<WordRecordEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 转换并保存
     * @param userId
     * @param openWord
     * @return
     */
    public Dict changeAndSave(Integer userId, String openWord) {
        String uuid = UUID.fastUUID().toString();
        WordRecordEntity entity = new WordRecordEntity();
        entity.setCreateTime(new Date());
        entity.setOpenWord(openWord);
        //entity.setPrivateWord(uuid);
        entity.setUserId(userId);
        entity.setUuId(uuid);

        save(entity);

        return Dict.create().set("uuid", uuid);
    }

    /**
     * 根据uuid获取单个
     * @param uuid
     * @return
     */
    public WordRecordEntity getByUUID(String uuid) {
        Assert.notEmpty(uuid, "请输入uuid");
        QueryWrapper wrapper = new QueryWrapper<WordRecordEntity>();
        wrapper.eq("uuid", uuid);
        WordRecordEntity entity = getOne(wrapper);
        return entity;
    }

    /**
     * 获取用户的
     *
     * @param userId
     * @return
     */
    public List<WordRecordEntity> listByUserId(Integer userId) {
        Assert.notNull(userId, "请输入userId");
        QueryWrapper wrapper = new QueryWrapper<WordRecordEntity>();
        wrapper.eq("user_id", userId);
        wrapper.orderBy(true, false, "create_time");
        return list(wrapper);
    }

}