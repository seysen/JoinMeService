package com.dataart.coreservice.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.dataart.coreservice.config.AwsCredentials
import com.dataart.coreservice.config.UploadPaths
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File

@Service
class AwsService(private val s3client: AmazonS3, val awsCredentials: AwsCredentials, val uploadPaths: UploadPaths) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun uploadToAws(storeName: String, storeDestination: File): String {

        logger.info(
            "uploading to aws file storeName: {}  from storeDestination: {}",
            storeName,
            storeName.toString()
        )

        s3client.putObject(
            PutObjectRequest(
                awsCredentials.s3Bucket,
                storeName, // UUID.randomUUID().toString() + "_" + originalFilename
                storeDestination // src/main/resources/static/upload
            )
        ) // сохраняем в бакет амазон с именем файл из storeDestination

        return uploadPaths.aws + "/" + storeName // <------ фронт запрашивает напрямую у амазон
        //   https://litvinovaprogrammersblog-storage.s3.us-east-2.amazonaws.com
    }
}
