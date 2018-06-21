package com.jh.jsuk.controller;


import com.google.common.collect.Maps;
import com.jh.jsuk.utils.ImgUtil;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * <p>
 * 上传图片OSS 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-27
 */
@Api(tags = "图片上传:")
@RestController
@RequestMapping("/upload")
public class UploadImgToOSSController {

    private static final String endpoint = "https://jsuke.oss-cn-shenzhen.aliyuncs.com/image/";
    private static Logger logger = Logger.getLogger(UserController.class);

    @ApiOperation("上传图片到OSS")
    @PostMapping(value = "/imgToOSS", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public Result imgToOSS(@ApiParam(value = "图片文件", required = true) @RequestParam(value = "file") MultipartFile file) {
        try {
            // 上传文件信息
            logger.info("OriginalFilename：" + file.getOriginalFilename());
            logger.info("ContentType：" + file.getContentType());
            logger.info("Name：" + file.getName());
            logger.info("Size：" + file.getSize());
            //TODO:文件大小、名称、类型检查的业务处理

            String uploads = ImgUtil.Uploads(file);
            System.out.println(uploads);
            return new Result().success("", endpoint + uploads);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().erro("上传图片失败,请稍后再试");
        }
    }

    @ApiIgnore
    @ApiOperation("上传图片到OSS")
    @PostMapping(value = "/imgToOSSByLayUi", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public Map imgToOSSByLayUi(@ApiParam(value = "图片文件", required = true) @RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> map = Maps.newHashMap();
        Map mp = Maps.newLinkedHashMap();
        try {
            // 上传文件信息
            logger.info("OriginalFilename：" + file.getOriginalFilename());
            logger.info("ContentType：" + file.getContentType());
            logger.info("Name：" + file.getName());
            logger.info("Size：" + file.getSize());
            //TODO:文件大小、名称、类型检查的业务处理

            String uploads = ImgUtil.Uploads(file);
            System.out.println(uploads);
            map.put("code", "0");
            mp.put("src", endpoint + uploads);
            map.put("msg", "上传成功");
            map.put("data", mp);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "-1");
            map.put("msg", "上传失败,请稍后再试");
            return map;
        }
    }
}

