package com.example.caronaapp.data.dto.vpic_carros

import com.google.gson.annotations.SerializedName

data class VpicCarrosModeloResponse(
    @SerializedName("Results") val results: List<Modelo>
) {
    data class Modelo(
        @SerializedName("Model_Name") val name: String
    )
}
