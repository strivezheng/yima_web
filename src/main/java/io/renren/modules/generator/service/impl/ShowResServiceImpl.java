package io.renren.modules.generator.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.constants.SysConfig;
import io.renren.modules.app.service.impl.RecordAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.ShowResDao;
import io.renren.modules.generator.entity.ShowResEntity;
import io.renren.modules.generator.service.ShowResService;


@Service("showResService")
public class ShowResServiceImpl extends ServiceImpl<ShowResDao, ShowResEntity> implements ShowResService {

    @Autowired
    SysConfig sysConfig;
    @Autowired
    RecordAsyncService recordAsyncService;
    @Autowired
    ShowResDao showResDao;

    public ShowResServiceImpl() {
    }

    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper wrapper = new QueryWrapper();
        String weight = (String) params.get("weight");
        if (StrUtil.isNotEmpty(weight)) {

            wrapper.eq("weight", weight);
        }
        IPage<ShowResEntity> page = this.page((new Query()).getPage(params), wrapper);
        return new PageUtils(page);
    }

    public PageUtils randomPage(Map<String, Object> params) {
        String v = String.valueOf(params.get("v"));
        String userIdStr = String.valueOf(params.get("userId"));
        Integer weight = (Integer) params.get("weight");
        Integer userId = null;
        weight = weight == null ? 1 : weight;

        try {
            userId = Integer.valueOf(userIdStr);
        } catch (Exception var14) {
            userId = null;
        }

        params.put("page", "1");
        Page page = (Page) (new Query()).getPage(params);
        List<ShowResEntity> taobaoList = this.showResDao.randomPage(page, "taobao", String.valueOf(weight));
        List<ShowResEntity> qipaList = this.showResDao.randomPage(page, "qipa", String.valueOf(weight));
        List<ShowResEntity> totalList = new ArrayList();
        totalList.addAll(taobaoList);
        totalList.addAll(qipaList);
        Collections.shuffle(totalList);
        List<Dict> resultList = new ArrayList();

        Dict dict;
        for (Iterator var10 = totalList.iterator(); var10.hasNext(); resultList.add(dict)) {
            ShowResEntity showResEntity = (ShowResEntity) var10.next();
            dict = Dict.create();
            if (this.sysConfig.showSecret(v)) {
                dict.set("showUrl", this.cleanpicUrl(showResEntity.getUrl()));
                dict.set("type", "image");
                dict.set("des", showResEntity.getDes());
                dict.set("avatar", showResEntity.getAvatar());
                dict.set("name", showResEntity.getName());
                String remark = showResEntity.getRemark();
                if (remark == null) {
                    dict.set("point", "0");
                } else {
                    dict.set("point", remark);
                }
            } else {
                this.buildTestData(dict, showResEntity);
            }
        }

        dict = Dict.create();
        this.buildAdvertismentData(dict);
        resultList.add(dict);
        if (userId != null) {
            List<Integer> resIdList = (List) totalList.stream().map(ShowResEntity::getId).collect(Collectors.toList());
            this.recordAsyncService.saveRecod(userId, resIdList);
            recordAsyncService.caculateWeight(userId);
        }

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(resultList);
        return pageUtils;
    }

    /**
     * 清洗数据，很多图片以_.webp结尾，导致ios端无法正常显示
     *
     * @param url
     * @return
     */
    private String cleanpicUrl(String url) {
        String endFlag = "_.webp";
        if (StrUtil.isEmpty(url)) {
            return this.sysConfig.getPromotionAdvertisement();
        } else {
            if (url.endsWith(endFlag)) {
            }

            url = url.replace(endFlag, "");
            return url;
        }
    }

    /**
     * 构造测试数据
     *
     * @param dict
     * @param showResEntity
     * @return
     */
    private Dict buildTestData(Dict dict, ShowResEntity showResEntity) {
        dict.set("showUrl", "https://ossweb-img.qq.com/images/lol/web201310/skin/big39000.jpg");
        dict.set("type", "image");
        dict.set("des", showResEntity.getDes());
        dict.set("avatar", showResEntity.getAvatar());
        dict.set("name", showResEntity.getName());
        dict.set("point", "0");
        return dict;
    }

    /**
     * 构造广告数据
     *
     * @param dict
     * @return
     */
    private Dict buildAdvertismentData(Dict dict) {
        dict.set("showUrl", this.sysConfig.getPromotionAdvertisement());
        dict.set("type", "image");
        dict.set("des", this.sysConfig.getPromotionDes());
        dict.set("avatar", this.sysConfig.getPromotionAvatar());
        dict.set("name", this.sysConfig.getPromotionTitle());
        dict.set("point", "99999");
        return dict;
    }


    /**
     * 修改权重
     *
     * @param id
     * @param weight
     * @return
     */
    public boolean updateWeight(Integer id, Integer weight) {

        ShowResEntity resEntity = new ShowResEntity();
        resEntity.setWeight(weight);
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("id", id);


        return update(resEntity, wrapper);

    }

}