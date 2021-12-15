package com.dataart.coreservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "upload")
@ConstructorBinding
class UploadPaths(
    val path: String,
    val url: String,
    val aws: String
)
