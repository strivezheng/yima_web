package io.renren.modules.generator.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.BrowseRecordDao;
import io.renren.modules.generator.entity.BrowseRecordEntity;
import io.renren.modules.generator.service.BrowseRecordService;


@Service("browseRecordService")
public class BrowseRecordServiceImpl extends ServiceImpl<BrowseRecordDao, BrowseRecordEntity> implements BrowseRecordService {

    @Autowired
    BrowseRecordDao browseRecordDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrowseRecordEntity> page = this.page(
                new Query<BrowseRecordEntity>().getPage(params),
                new QueryWrapper<BrowseRecordEntity>()
        );

        return new PageUtils(page);
    }

    public BrowseRecordEntity getRecord(Integer userId, Integer resId) {
        QueryWrapper<BrowseRecordEntity> wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        wrapper.eq("res_id", resId);
        return (BrowseRecordEntity)this.getOne(wrapper);
    }

    /**
     * 获取用户浏览次数
     * @param userId
     * @return map
     *          userId
     *          readTimes：总阅读次数（一条资源可能被看了多次）
     *          resTimes：浏览资源条数
     */
    public Map getUserVisitTimes(Integer userId){
        Map map = browseRecordDao.getUserVisitTimes(userId);
        return map;
    }

    /**
     * 统计浏览量
     * @param params：userId,isAsc
     * @return
     */
    public PageUtils statisticsUserVisitRecord(Integer userId,Map<String, Object> params){
//        Integer userId = (Integer) params.get("userId");
        String  isAsc = (String) params.get("isAsc");

        IPage page =  new Query().getPage(params,"resTimes",Boolean.valueOf(isAsc));
        List<Map> list = browseRecordDao.statisticsUserVisitRecord(page,userId);
        page.setRecords(list);

        return new PageUtils(page);

    }
}