package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.ImageUrlDataInfoDao;
import io.renren.modules.generator.entity.ImageUrlDataInfoEntity;
import io.renren.modules.generator.service.ImageUrlDataInfoService;


@Service("imageUrlDataInfoService")
public class ImageUrlDataInfoServiceImpl extends ServiceImpl<ImageUrlDataInfoDao, ImageUrlDataInfoEntity> implements ImageUrlDataInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ImageUrlDataInfoEntity> page = this.page(
                new Query<ImageUrlDataInfoEntity>().getPage(params),
                new QueryWrapper<ImageUrlDataInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据fileId，查询图片链接信息
     * @param fileId
     * @return
     */
    public List<ImageUrlDataInfoEntity> getImageUrlInfoByFileId(int fileId){
        QueryWrapper<ImageUrlDataInfoEntity> wrapper = new QueryWrapper();
        wrapper.eq("file_id",fileId);

       List<ImageUrlDataInfoEntity> list = list(wrapper);
       return list;

    }

}