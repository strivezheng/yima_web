package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.FileInfoDao;
import io.renren.modules.generator.entity.FileInfoEntity;
import io.renren.modules.generator.service.FileInfoService;


@Service("fileInfoService")
public class FileInfoServiceImpl extends ServiceImpl<FileInfoDao, FileInfoEntity> implements FileInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FileInfoEntity> page = this.page(
                new Query<FileInfoEntity>().getPage(params),
                new QueryWrapper<FileInfoEntity>()
        );

        return new PageUtils(page);
    }

}