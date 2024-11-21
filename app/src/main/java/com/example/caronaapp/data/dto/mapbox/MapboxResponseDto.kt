package com.example.caronaapp.data.dto.mapbox

data class MapboxResponseDto(
    val routes: List<Route>,
    val code: String
) {
    data class Route(
        val duration: Double,
        val distance: Double,
    )
}
