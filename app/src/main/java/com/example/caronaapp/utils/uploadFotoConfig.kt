package com.example.caronaapp.utils

import android.content.Context
import android.net.Uri
import com.example.caronaapp.data.dto.cloudinary.CloudinaryBodyUploadDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import java.security.MessageDigest

private const val CLOUD_NAME = "carona"
private const val API_KEY = "463869141171645"
private const val API_SECRET = "ANW652Uab22jHdLKT2xF8llg8dI"
private const val UPLOAD_PRESET = "profile_pictures"
private val TIMESTAMP = (System.currentTimeMillis() / 1000).toString()
private val SIGNATURE = generateSignature()

private fun uriToFile(context: Context, uri: Uri): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream?.copyTo(outputStream)
    }
    return tempFile
}

private fun generateSignature(): String {
    val signatureData = "timestamp=${TIMESTAMP.toLong()}&upload_preset=$UPLOAD_PRESET$API_SECRET"
    return signatureData.toByteArray().let { MessageDigest.getInstance("SHA-1").digest(it) }
        .joinToString("") { "%02x".format(it) }
}

fun configPhotoToUpload(context: Context, uri: Uri): CloudinaryBodyUploadDto {
    val file = uriToFile(context, uri)
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

    return CloudinaryBodyUploadDto(
        cloudName = CLOUD_NAME,
        body = body,
        uploadPreset = UPLOAD_PRESET.toRequestBody("text/plain".toMediaTypeOrNull()),
        apiKey = API_KEY.toRequestBody("text/plain".toMediaTypeOrNull()),
        timestamp = TIMESTAMP.toRequestBody("text/plain".toMediaTypeOrNull()),
        signature = SIGNATURE.toRequestBody("text/plain".toMediaTypeOrNull())
    )
}

