package com.example.caronaapp.data.dto.google_maps

import com.google.gson.annotations.SerializedName

data class GeocodeResponse(
    val results: List<Result>,
    val status: String
) {
    data class Result(
        val geometry: Geometry,
        val formatted_address: String,
        val address_components: List<AddressComponent>
    )

    data class Geometry(
        val location: Location
    )

    data class Location(
        @SerializedName("lat") val latitude: Double,
        @SerializedName("lng") val longitude: Double,
    )

    data class AddressComponent(
        val long_name: String,
        val short_name: String,
        val types: List<String>
    )
}