package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.cloudinary.CloudinaryResponseDto
import com.example.caronaapp.service.api.CloudinaryApi
import com.example.caronaapp.service.repository.CloudinaryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class CloudinaryRepositoryImpl(private val cloudinaryApi: CloudinaryApi) : CloudinaryRepository {
    override suspend fun upload(
        cloudName: String,
        file: MultipartBody.Part,
        uploadPreset: RequestBody,
        apiKey: RequestBody,
        timestamp: RequestBody,
        signature: RequestBody
    ): Response<CloudinaryResponseDto> {
        return cloudinaryApi.upload(cloudName, file, uploadPreset, apiKey, timestamp, signature)
    }
}