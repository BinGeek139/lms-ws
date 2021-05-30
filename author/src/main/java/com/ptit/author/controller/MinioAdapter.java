package com.ptit.author.controller;


import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class MinioAdapter {
    @Value("${minio.buckek.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String ip;

    /**
     * Login account
     */
    @Value("${minio.access.name}")
    private String accessKey;

    /**
     * login password
     */
    @Value("${minio.access.secret}")
    private String secretKey;


    public String uploadFile(MultipartFile file){

        try {
            MinioClient minioClient = new MinioClient("http://" + ip, accessKey, secretKey);

            boolean bucketExists = minioClient.bucketExists(bucketName);
            if (bucketExists) {
                log.info("Warehouse" + bucketName + "Already exists, you can upload files directly.");
            } else {
                minioClient.makeBucket(bucketName);
            }
            String fileName=file.getOriginalFilename();
            String[] names=fileName.split("\\.");
            fileName= UUID.randomUUID().toString()+"."+names[1];
            if (file.getSize() <= 20971520) {
                // fileName is empty, indicating to upload using the source file name
                if (fileName == null) {
                    fileName = file.getOriginalFilename();
                    fileName = fileName.replaceAll(" ", "_");
                }
                minioClient.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
                log.info("File successfully uploaded" + fileName + "to" + bucketName);
                String url=minioClient.getObjectUrl(bucketName,fileName);
                return url;
            } else {
                throw new Exception("Please upload a file smaller than 20mb");
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("ORA")) {
                return "";
            }
            return "";
        }
    }
    /**
     * Determine whether the file exists
     * @param fileName file name
     * @param bucketName bucket name (folder)
     * @return
     */
    public boolean isFileExisted(String fileName, String bucketName) {
        InputStream inputStream = null;
        try {
            MinioClient minioClient = new MinioClient("http://" + ip, accessKey, secretKey);
            inputStream = minioClient.getObject(bucketName, fileName);
            if (inputStream != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Delete Files
     * @param bucketName bucket name (folder)
     * @param fileName file name
     * @return
     */
    public boolean delete(String bucketName,String fileName) {
        try {
            MinioClient minioClient = new MinioClient("http://" + ip, accessKey, secretKey);
            minioClient.removeObject(bucketName,fileName);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
