package com.zxj.finalexam.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileTransProvider {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    @Value("${aliyun.oss.endpoint}")
    private  String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private  String accessKeyId ;
    @Value("${aliyun.oss.accessKeySecret}")
    private  String accessKeySecret ;
    @Value("${aliyun.oss.bucketName}")
    private  String bucketName;

    /**
     * markdown上传文件
     * @param inputStream 上传文件流
     * @param fileName  文件名
     * @return  上传到oos随机产生的文件名字
     */
  /*  public String upload(InputStream inputStream, String fileName) {

        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if(suffixName.isBlank())
            return null;
        // 生成上传文件名
        String finalFileName = UUID.randomUUID() + "" + suffixName;
        System.out.println(finalFileName);
        String objectName =  "imags/" + finalFileName;

        // 创建PutObjectRequest对象。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件。
        ossClient.putObject(bucketName,objectName,inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        return objectName;

    }*/

    public String upload(InputStream inputStream, String fileName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if(StringUtils.isBlank(suffixName))
            return null;


        // 生成上传文件名
        String finalFileName = UUID.randomUUID() + "" + suffixName;
        System.out.println(finalFileName);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String yMd=format.format(new Date());
        String objectName =null;
        if (suffixName.equals(".jpg")||suffixName.equals(".png")){
            objectName="images/" +yMd+"/"+ finalFileName;
        }else if (suffixName.equals(".mp3")||suffixName.equals(".mp4")){
            objectName =  "videos/" +yMd+"/"+ finalFileName;
        }else if(suffixName.equals(".docx")||suffixName.equals(".pdf")||suffixName.equals(".doc")||suffixName.equals(".xlsx")||suffixName.equals(".zip")||suffixName.equals(".pptx")||suffixName.equals(".txt")){
            objectName =  "multi/" +yMd+"/"+ finalFileName;
        }


        // 生成签名URL。
        Date expiration = null;
        try {
            expiration = DateUtil.parseRfc822Date("Thu, 19 Mar 2030 00:00:00 GMT");
        } catch (ParseException e) {
            System.out.println("set expiration date error");
        }

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.PUT);
        String style = "video/snapshot,t_50000,f_jpg,w_800,h_600";
        request.setProcess(style);
        // 设置过期时间。
        request.setExpiration(expiration);
        // 设置ContentType。
        request.setContentType("application/octet-stream");
        // 添加用户自定义元信息。
        request.addUserMetadata("author", "aliy");
        // 通过HTTP PUT请求生成签名URL。
        URL signedUrl = ossClient.generatePresignedUrl(request);
        String imgUrl=signedUrl.toString();
//http://orangecommunity.oss-cn-beijing.aliyuncs.com/imags/7r9879860-4c8c-4e4a-969a-c6c9347e0d76.jpg?Expires=1900108800&OSSAccessKeyId=LTAI4G1rKQKdkoJGaF5ADE4X&Signature=ZBBMsS2RlF%2B8eVRqXxnAjxYTvQc%3D

        // 使用签名URL发送请求。
        // 添加PutObject请求头。
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("Content-Type", "application/octet-stream");
        customHeaders.put("x-oss-meta-author", "aliy");

        try {
            ossClient.putObject(signedUrl, inputStream, inputStream.available(), customHeaders);
        } catch (IOException e) {
            System.out.println("get file bytes length fail");
        }
        // 关闭OSSClient。
        ossClient.shutdown();

        return imgUrl.substring(imgUrl.indexOf("http:"),imgUrl.indexOf("?"));

    }

}