package com.liu.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.liu.community.exception.CustomizeErrorCode;
import com.liu.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UcloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.suffix}")
    private String suffix;

    @Value("${ucloud.ufile.expires}")
    private Integer expires;
    // Bucket相关API的授权器




    public String upload(InputStream filestream,String mizeType,String fileName){
        File file = new File("your file path");
        String[] fileSplter=fileName.split("\\.");//转移
        String generateFileName;
        if (fileSplter.length>1){
            generateFileName= UUID.randomUUID().toString()+"."+fileSplter[fileSplter.length-1];
        }else{
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, suffix);
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
                    publicKey, privateKey);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(filestream,mizeType )
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(objectAuthorization, config)
                        .getDownloadUrlFromPrivateBucket(generateFileName, bucketName, expires)
                        .createUrl();
                return url;
            }   else {

            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

    }
}
