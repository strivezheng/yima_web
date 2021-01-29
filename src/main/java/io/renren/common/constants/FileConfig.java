package io.renren.common.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
        prefix = "file-config"
)
@Data
public class FileConfig {

    /**
     * 临时图片存放地址
     */
    private String tmpImgDir;

    /**
     * 主要上传的图床平台
     */
    private Integer mainPlatform;

}
