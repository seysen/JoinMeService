package com.dataart.coreservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig : WebMvcConfigurer {
    @Value("\${upload.path}")
    var uploadPath: String? = null // path: src/main/resources/static/upload

    @Value("\${upload.url}")
    var uploadUrl: String? = null //  url: /usr_img/ те по этому url файлы ищутся в /upload

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("$uploadUrl**").addResourceLocations("file:$uploadPath/")
    }
}
