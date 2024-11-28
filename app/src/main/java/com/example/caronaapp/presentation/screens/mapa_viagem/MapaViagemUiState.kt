package com.example.caronaapp.presentation.screens.mapa_viagem

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

data class MapaViagemUiState(
    val isLoadingScreen: Boolean = true,
    val routePoints: List<LatLng> = emptyList(),
    val isMapLoaded: Boolean = false,
    val fotoUserAsBitmap: BitmapDescriptor? = null,
    val pontoPartida: LatLng? = null,
    val pontoDestino: LatLng? = null,
    val distancia: Double = 0.0,
    val duracao: String = "",
    val isError: Boolean = false,
)