package io.renren.common.constants;

import java.util.List;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
        prefix = "sys-config"
)
@Data
public class SysConfig {
    private String secretVersion;
    private String promotionAdvertisement;
    private String promotionTitle;
    private String promotionDes;
    private String promotionAvatar;
    private Integer weight1;
    private Integer weight2;
    private Integer weight3;
    private Integer weight4;
    private Integer weight5;


    public boolean showSecret(String v) {
        if (StrUtil.isEmpty(v)) {
            return false;
        } else {
            List<String> showList = StrUtil.splitTrim(this.secretVersion, ",");
            return showList.contains(v);
        }
    }
}
