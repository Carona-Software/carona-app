package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.cloudinary.CloudinaryUploadDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Part

interface CloudinaryRepository {

    @POST
    suspend fun upload(
        @Part("file") file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: String
    ): Response<CloudinaryUploadDto>
}