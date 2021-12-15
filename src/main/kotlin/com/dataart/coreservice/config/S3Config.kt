package com.dataart.coreservice.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(val awsCredentialsConfig: AwsCredentials) {

    @Bean
    fun s3client(): AmazonS3 {
        val awsCreds = BasicAWSCredentials(
            awsCredentialsConfig.awsAccessKeyId, awsCredentialsConfig.awsSecretAccessKey
        )
        val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(awsCredentialsConfig.s3Region))
            .withCredentials(AWSStaticCredentialsProvider(awsCreds))
            .build()

        return s3Client
    }
}
