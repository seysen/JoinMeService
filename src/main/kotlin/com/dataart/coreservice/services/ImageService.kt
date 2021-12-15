package com.dataart.coreservice.services

import com.dataart.coreservice.config.UploadPaths
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Files
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.Path

@Service
class ImageService(private val awsService: AwsService, val uploadPaths: UploadPaths) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun upload(image: MultipartFile): String {

        // слишком большое изображение обработать на фронте!

        checkIfImageFolderExists()

        image.originalFilename.apply {

            (UUID.randomUUID().toString() + "_" + this).let {

                File(uploadPaths.path + "/" + it).let { destination: File ->
                    saveImageToAppMemory(image, this!!, destination)
                    // сохраняем в бакет амазон с именем файл из storeDestination
                    return awsService.uploadToAws(it, destination)
                }
            }
        }
        // для сохранения ТОЛЬКО в памяти приложения:
        //  return uploadPaths.url  + storeName ; // /usr_img/ +  UUID.randomUUID().toString() + "_" + originalFilename
    }

    private fun saveImageToAppMemory(image: MultipartFile, originalFilename: String, storeDestination: File) {
        ByteArrayInputStream(image.bytes).use {
            ImageIO.read(it).let {
                ImageIO.write(it, getFileType(originalFilename), storeDestination)
            }
        }

        logger.info(
            "image originalFilename: {} saved to storeDestination: {}",
            originalFilename,
            storeDestination.toString()
        )
    }

    private fun getFileType(originalFilename: String): String = with(originalFilename) {

        (this.lastIndexOf(".") ?: -1).let {
            this.substring(it + 1) ?: ""
        }.also {
            logger.info("file type determined: {} ", it)
            return it
        }
    }

    private fun checkIfImageFolderExists() {
        if (!Files.exists(Path(uploadPaths.path))) {
            File(uploadPaths.path).mkdirs()
            logger.info("uploadPath: {} created", uploadPaths.path)
        }
    }
}
