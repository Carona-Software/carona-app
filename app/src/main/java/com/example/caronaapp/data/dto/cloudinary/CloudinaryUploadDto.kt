package com.example.caronaapp.data.dto.cloudinary

import com.google.gson.annotations.SerializedName

data class CloudinaryUploadDto(
    @SerializedName("secure_url") val url: String
)
