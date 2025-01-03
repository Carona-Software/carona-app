package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.cloudinary.CloudinaryResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CloudinaryApi {

    @Multipart
    @POST("{cloud_name}/image/upload")
    suspend fun upload(
        @Path("cloud_name") cloudName: String,
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody,
        @Part("api_key") apiKey: RequestBody,
        @Part("timestamp") timestamp: RequestBody,
        @Part("signature") signature: RequestBody
    ): Response<CloudinaryResponseDto>
}