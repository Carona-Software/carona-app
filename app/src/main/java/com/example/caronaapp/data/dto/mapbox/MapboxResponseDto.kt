package com.example.caronaapp.data.dto.mapbox

data class MapboxResponseDto(
    val routes: List<Route>,
    val code: String
) {
    data class Route(
        val duration: Double,
        val distance: Double,
        val geometry: Geometry
    )

    data class Geometry(
        val coordinates: List<List<Double>>
    )
}
