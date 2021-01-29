//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.renren.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.renren.common.constants.SysConfig;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.service.UserService;
import io.renren.modules.generator.entity.BrowseRecordEntity;
import io.renren.modules.generator.service.BrowseRecordService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RecordAsyncService {
    @Autowired
    BrowseRecordService browseRecordService;

    @Autowired
    SysConfig sysConfig;

    @Autowired
    UserService userService;

    public RecordAsyncService() {
    }

    @Async
    public String saveRecod(Integer userId, List<Integer> resIdList) {
        if (userId == null) {
            return "";
        } else if (resIdList != null && !resIdList.isEmpty()) {
            Iterator var3 = resIdList.iterator();

            while (var3.hasNext()) {
                Integer resId = (Integer) var3.next();
                BrowseRecordEntity browseRecordEntity = this.browseRecordService.getRecord(userId, resId);
                if (browseRecordEntity != null) {
                    Integer times = browseRecordEntity.getTimeFlag();
                    times = times == null ? 0 : times;
                    browseRecordEntity.setTimeFlag(times + 1);
                    browseRecordEntity.setUpdateTime(new Date());
                    this.browseRecordService.updateById(browseRecordEntity);
                } else {
                    browseRecordEntity = new BrowseRecordEntity();
                    browseRecordEntity.setResId(resId);
                    browseRecordEntity.setUserId(userId);
                    browseRecordEntity.setTimeFlag(1);
                    browseRecordEntity.setCreateTime(new Date());
                    this.browseRecordService.save(browseRecordEntity);
                }
            }

            return "success";
        } else {
            return "";
        }
    }

    /**
     * 计算用户权重
     * @param userId
     * @return
     */
    @Async
    public boolean caculateWeight(Integer userId) {

        Map map = browseRecordService.getUserVisitTimes(userId);
        Integer readTimes = Integer.valueOf(String.valueOf(map.get("readTimes")));
        Integer resTimes = Integer.valueOf(String.valueOf(map.get("resTimes")));

        readTimes = readTimes == null ? 0 : readTimes;
        resTimes = resTimes == null ? 0 : resTimes;
        Integer weight = 1;
        if (readTimes > sysConfig.getWeight2()) {
            weight = 2;
        } else if (readTimes > sysConfig.getWeight3()) {
            weight = 3;
        } else if (readTimes > sysConfig.getWeight4()) {
            weight = 4;
        } else if (readTimes > sysConfig.getWeight5()) {
            weight = 5;
        } else {
            weight = 1;
        }
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userId);

        UserEntity userEntity = new UserEntity();
        userEntity.setWeight(weight);

        userService.update(userEntity, updateWrapper);


        return true;
    }
}
