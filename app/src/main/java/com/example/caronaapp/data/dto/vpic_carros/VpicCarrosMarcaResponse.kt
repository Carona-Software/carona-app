package com.example.caronaapp.data.dto.vpic_carros

import com.google.gson.annotations.SerializedName

data class VpicCarrosMarcaResponse(
    @SerializedName("Results") val results: List<Marca>
) {
    data class Marca(
        @SerializedName("MakeName") val name: String
    )
}
