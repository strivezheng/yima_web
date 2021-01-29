package io.renren.modules.generator.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.AimUserAccountDao;
import io.renren.modules.generator.entity.AimUserAccountEntity;
import io.renren.modules.generator.service.AimUserAccountService;


@Service("aimUserAccountService")
@Slf4j
public class AimUserAccountServiceImpl extends ServiceImpl<AimUserAccountDao, AimUserAccountEntity> implements AimUserAccountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AimUserAccountEntity> page = this.page(
                new Query<AimUserAccountEntity>().getPage(params),
                new QueryWrapper<AimUserAccountEntity>()
        );

        return new PageUtils(page);
    }


    public String importEmailAccount(List<AimUserAccountEntity> aimUserAccountList) {
        log.info("批量导入发送目标:{}", aimUserAccountList.size());
        this.saveBatch(aimUserAccountList);
        return aimUserAccountList.size() + "";
    }

    public Map sendTest(int userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        List<AimUserAccountEntity> aimUserAccountList = this.list(wrapper);
        log.info("目标人数：{}", userId);
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("title", "个人简历");
        paramMap.put("content", "Java开发个人简历，也懂前端");
        paramMap.put("attachmentName", "个人简历.doc");
        paramMap.put("attachmentFile", FileUtil.file("C:\\Users\\seven\\Desktop\\个人简历.doc"));
        Dict dict = Dict.create();

        AimUserAccountEntity aimUserAccountEntity;
        String result;
        for(Iterator var6 = aimUserAccountList.iterator(); var6.hasNext(); dict.set(aimUserAccountEntity.getEmail(), result)) {
            aimUserAccountEntity = (AimUserAccountEntity)var6.next();
            paramMap.put("to", aimUserAccountEntity.getEmail());
            result = HttpUtil.post(" http://127.0.0.1:12345/api/v0.0.1/mail/send", paramMap);
            log.info("请求结果：{}", result);

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var10) {
                var10.printStackTrace();
            }
        }

        return dict;
    }

}