package com.dataart.coreservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "jsa")
@ConstructorBinding
class AwsCredentials(
    val awsAccessKeyId: String, // Spring uses some relaxed rules for binding properties.
    val awsSecretAccessKey: String,
    val s3Bucket: String,
    val s3Region: String
)
