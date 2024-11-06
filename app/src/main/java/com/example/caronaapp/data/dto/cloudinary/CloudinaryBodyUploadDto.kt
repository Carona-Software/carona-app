package com.example.caronaapp.data.dto.cloudinary

import okhttp3.MultipartBody
import okhttp3.RequestBody

class CloudinaryBodyUploadDto(
    val cloudName: String,
    val body: MultipartBody.Part,
    val uploadPreset: RequestBody,
    val apiKey: RequestBody,
    val timestamp: RequestBody,
    val signature: RequestBody,
)