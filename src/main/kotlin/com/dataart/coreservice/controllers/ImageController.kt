package com.dataart.coreservice.controllers

import com.dataart.coreservice.services.ImageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/image")
class ImageController(private val imageService: ImageService) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun add(@RequestParam image: MultipartFile): String {
        logger.info("addImage request: {}", image.originalFilename)
        imageService.upload(image).let {
            logger.info("addEvent response: {}", it)
            return it
        }
    }
}
