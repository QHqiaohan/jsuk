package com.jh.jsuk.utils;


import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyq on 2017/7/5.
 */
public class ImgUtil {

    public static String GenerateImage(String imgStr, String imgFilePath) {   //对字节数组字符串进行Base64解码并生成图片
       /* if (imgStr == null) //图像数据为空
            return null;
        Pattern p = Pattern.compile("data:image/(.*?);base64,");
        Matcher m = p.matcher(imgStr);
        String suffix = null;
        if(m.find()) {
            suffix = m.group(1);
        }
        imgStr = imgStr.replaceAll("data:image/(.*?);base64,","");*/
        String suffix = "png";
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath + "." + suffix);
            out.write(b);
            out.flush();
            out.close();
            System.out.println(imgFilePath.substring(imgFilePath.lastIndexOf("/") + 1) + "." + suffix);
            return imgFilePath.substring(imgFilePath.lastIndexOf("/") + 1) + "." + suffix;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 上传图片 nginx
     *
     * @param
     */
    public static List<String> Upload(String[] files) {
        List<String> fileList = new ArrayList<>();
        try {
            for (String file :
                    files) {
                String path = ImgUtil.GenerateImage(file.split(",")[1], "E:/nginx-1.12.1/img/" + System.currentTimeMillis());
                fileList.add(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }


    /**
     * oss
     *
     * @param files
     * @return
     */
    public static List<String> Uploads(String[] files) {
        List<String> fileList = new ArrayList<>();
        try {
            for (String file : files) {
                //String path = ImgUtil.GenerateImage(file.split(",")[1], "E:/nginx-1.12.1/img/" + System.currentTimeMillis());

                //////////////////////
                String suffix = "png";

                //Base64解码
                byte[] b = Base64.decodeBase64(file.replaceAll("data:image/(.*?);base64,", ""));
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {//调整异常数据
                        b[i] += 256;
                    }
                }
                //生成jpeg图片
                String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
                // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，
                // 创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
                //String accessKeyId = "LTAI6vqQNe1Txm2D";
                String accessKeyId = "LTAIDWfs6ZTBWc7d";
                //String accessKeySecret = "EJn6go2fDybCdvSpFaZqqDxJTM1xnW";
                String accessKeySecret = "e6qcAq9lbZizAIOUJoUdnIPVKnrtfs";
                // 创建OSSClient实例
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                // 上传文件
                String key = System.currentTimeMillis() + ".png";

                PutObjectResult putObject = ossClient.putObject("youmehe", "image/" + key, new ByteArrayInputStream(b));
                System.out.println(putObject.getETag());
                fileList.add(key);
                // 关闭client
                ossClient.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public static List<String> Uploads(MultipartFile[] files, boolean isUrl) {
        List<String> fileList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                byte[] b = file.getBytes();
                //生成jpeg图片
                String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
                // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，
                // 创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
                String accessKeyId = "LTAIDWfs6ZTBWc7d";
                String accessKeySecret = "e6qcAq9lbZizAIOUJoUdnIPVKnrtfs";
                // 创建OSSClient实例
                OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
                // 上传文件
                String key = System.currentTimeMillis() + ".png";

                PutObjectResult putObject = ossClient.putObject("youmehe", "image/" + key, new ByteArrayInputStream(b));
                System.out.println(putObject.getETag());
                if (isUrl) {
                    fileList.add("http://jsuke.oss-cn-shenzhen.aliyuncs.com/image/" + key);
                } else {
                    fileList.add(key);
                }
                // 关闭client
                ossClient.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public static String    Uploads(MultipartFile file) {
        List<String> fileList = new ArrayList<>();
        String key = System.currentTimeMillis() + RandomUtil.randomNumbers(6) + ".png";
        try {
            byte[] b = file.getBytes();
            //生成jpeg图片
            String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，
            // 创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
            String accessKeyId = "LTAIDWfs6ZTBWc7d";
            String accessKeySecret = "e6qcAq9lbZizAIOUJoUdnIPVKnrtfs";

            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件

            PutObjectResult putObject = ossClient.putObject("jsuke", "image/" + key, new ByteArrayInputStream(b));
            fileList.add(key);
            // 关闭client
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
}
