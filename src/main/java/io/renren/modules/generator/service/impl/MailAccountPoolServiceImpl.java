package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.MailAccountPoolDao;
import io.renren.modules.generator.entity.MailAccountPoolEntity;
import io.renren.modules.generator.service.MailAccountPoolService;


@Service("mailAccountPoolService")
public class MailAccountPoolServiceImpl extends ServiceImpl<MailAccountPoolDao, MailAccountPoolEntity> implements MailAccountPoolService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MailAccountPoolEntity> page = this.page(
                new Query<MailAccountPoolEntity>().getPage(params),
                new QueryWrapper<MailAccountPoolEntity>()
        );

        return new PageUtils(page);
    }

}