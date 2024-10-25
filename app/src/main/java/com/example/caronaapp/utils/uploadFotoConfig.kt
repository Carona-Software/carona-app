package com.example.caronaapp.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.caronaapp.service.RetrofitService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import java.security.MessageDigest

val CLOUD_NAME = "carona"
val API_KEY = "463869141171645"
val API_SECRET = "ANW652Uab22jHdLKT2xF8llg8dI"
val UPLOAD_PRESET = "profile_pictures"
val TIMESTAMP = (System.currentTimeMillis() / 1000).toString()
val SIGNATURE = generateSignature()

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream?.copyTo(outputStream)
    }
    return tempFile
}

fun generateSignature(): String {
    val signatureData = "timestamp=${TIMESTAMP.toLong()}&upload_preset=$UPLOAD_PRESET$API_SECRET"
    return signatureData.toByteArray().let { MessageDigest.getInstance("SHA-1").digest(it) }
        .joinToString("") { "%02x".format(it) }
}

suspend fun uploadPhotoToCloudinary(context: Context, uri: Uri): String? {
    try {
        val file = uriToFile(context, uri)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val requestBodyUploadPreset = UPLOAD_PRESET.toRequestBody("text/plain".toMediaTypeOrNull())

        val requestBodyApiKey = API_KEY.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodyTimestamp = TIMESTAMP.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodySignature = SIGNATURE.toRequestBody("text/plain".toMediaTypeOrNull())

        val response = RetrofitService.getApiCloudinaryRepository().upload(
            CLOUD_NAME,
            body,
            requestBodyUploadPreset,
            requestBodyApiKey,
            requestBodyTimestamp,
            requestBodySignature
        )

        if (response.isSuccessful) {
            Log.i(
                "uploadFoto", "Sucesso ao fazer upload de foto pro Cloudinary: ${response.body()}"
            )
            return response.body()!!.url
        } else {
            Log.e(
                "uploadFoto", "Erro ao fazer upload de foto pro Cloudinary: ${response.errorBody()}"
            )
            return null
        }
    } catch (e: Exception) {
        Log.e(
            "uploadFoto",
            "Exception -> erro ao fazer upload de foto pro Cloudinary: ${e.printStackTrace()}"
        )
        return null
    }
}

