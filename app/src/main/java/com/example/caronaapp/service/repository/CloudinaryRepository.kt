package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.cloudinary.CloudinaryResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface CloudinaryRepository {

    suspend fun upload(
        cloudName: String,
        file: MultipartBody.Part,
        uploadPreset: RequestBody,
        apiKey: RequestBody,
        timestamp: RequestBody,
        signature: RequestBody
    ): Response<CloudinaryResponseDto>
}