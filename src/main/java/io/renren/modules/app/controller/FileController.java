package io.renren.modules.app.controller;


import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import io.renren.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * 文件上传
 */
@RestController
@RequestMapping({"file/open"})
@Api()
public class FileController {


    /**
     * 获取 https://yima.lanzou.cloud/res/img/217491aa-2cb8-4b8d-9d7f-e486c7737c01.jpg
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping(value = "/fileUpload")
    @ApiOperation("文件上传")
    public R fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = getUploadPath(); // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        String compressFileName = filePath + UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片压缩
        Img.from(dest)
                .setQuality(0.5)//压缩比率
                .write(FileUtil.file(compressFileName));
        return R.ok().setData(compressFileName);
    }

    private String getUploadPath() {

        File directory = new File("");// 参数为空
        String courseFile = "";
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = "";
        if (courseFile.contains(":")) {
            //windows 系统
            path = "D://temp-rainy//";
        } else {
            //linux 系统
            path = "/mnt/file/img/";

        }
        return path;
    }

}
