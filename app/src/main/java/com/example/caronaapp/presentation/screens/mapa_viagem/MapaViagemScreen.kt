package com.example.caronaapp.presentation.screens.mapa_viagem

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.presentation.view_models.MapaViagemViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulRota
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.Horario
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.utils.functions.formatCep
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapaViagemScreen(
    navController: NavController,
    viagemId: Int,
    pontoPartida: Coordenadas,
    viewModel: MapaViagemViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viagemData by viewModel.viagemData.collectAsState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, key2 = state.isMapLoaded) {
        viewModel.getDetalhesViagem(viagemId)

        if (state.isMapLoaded) {
            viewModel.setFotoUserBitmap(context)
        }

        if (state.isError) {
            Toast.makeText(
                context,
                "Não foi possível carregar os detalhes da viagem",
                Toast.LENGTH_LONG
            ).show()
            delay(300)
            viewModel.setControlVariablesToFalse()
        }
    }

    val pontoPartidaMarkerState = state.pontoPartida?.let { rememberMarkerState(position = it) }
    val pontoDestinoMarkerState = state.pontoDestino?.let { rememberMarkerState(position = it) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(pontoPartida.latitude, pontoPartida.longitude), 12f
        )
    }

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                ) {
                    Column {
                        TopBarTitle(
                            navController = navController,
                            title = stringResource(id = R.string.viagem)
                        )

                        if (!state.isLoadingScreen && viagemData != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                border = BorderStroke(1.dp, CinzaE8),
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .padding(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                modifier = Modifier.padding(),
                                                imageVector = PontoPartida,
                                                contentDescription = "Voltar",
                                                tint = Azul,
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = stringResource(
                                                    id = R.string.viagem_endereco_completo,
                                                    viagemData?.trajeto?.pontoPartida?.logradouro
                                                        ?: "--",
                                                    viagemData?.trajeto?.pontoPartida?.numero ?: 0,
                                                    formatCep(
                                                        viagemData?.trajeto?.pontoPartida?.cep ?: ""
                                                    ),
                                                    viagemData?.trajeto?.pontoPartida?.cidade ?: "",
                                                    viagemData?.trajeto?.pontoPartida?.uf ?: "",
                                                ),
                                                style = MaterialTheme.typography.displayLarge,
                                                color = Azul,
                                                modifier = Modifier.fillMaxWidth(),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                modifier = Modifier.padding(),
                                                imageVector = Localizacao,
                                                contentDescription = "Chegada",
                                                tint = Azul,
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = stringResource(
                                                    id = R.string.viagem_endereco_completo,
                                                    viagemData?.trajeto?.pontoChegada?.logradouro
                                                        ?: "--",
                                                    viagemData?.trajeto?.pontoChegada?.numero ?: 0,
                                                    formatCep(
                                                        viagemData?.trajeto?.pontoChegada?.cep ?: ""
                                                    ),
                                                    viagemData?.trajeto?.pontoChegada?.cidade ?: "",
                                                    viagemData?.trajeto?.pontoChegada?.uf ?: "",
                                                ),
                                                style = MaterialTheme.typography.displayLarge,
                                                color = Azul,
                                                modifier = Modifier.fillMaxWidth(),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Horario,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Azul
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = state.duracao,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Azul
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "(${state.distancia.toInt()} km)",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Cinza90
                                )
                            }

                            HorizontalDivider(thickness = 2.dp, color = CinzaE8)
                        }
                    }
                }

                if (state.isLoadingScreen) {
                    LoadingScreen()
                } else {
                    if (viagemData == null) {
                        NoResultsComponent(text = stringResource(id = R.string.sem_conteudo_detalhes_viagem))
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            GoogleMap(
                                modifier = Modifier.fillMaxSize(),
                                cameraPositionState = cameraPositionState,
                                onMapLoaded = { viewModel.setMapLoaded() }
                            ) {
                                if (
                                    state.pontoPartida != null &&
                                    state.pontoDestino != null &&
                                    state.isMapLoaded
                                ) {
                                    Marker(
                                        state = pontoPartidaMarkerState!!,
                                        title = "Partida",
                                        snippet = stringResource(
                                            id = R.string.viagem_endereco_simples,
                                            viagemData?.trajeto?.pontoPartida?.logradouro ?: "--",
                                            viagemData?.trajeto?.pontoPartida?.numero ?: 0
                                        ),
                                        icon = state.fotoUserAsBitmap,
                                    )
                                    Marker(
                                        state = pontoDestinoMarkerState!!,
                                        title = "Destino",
                                        snippet = stringResource(
                                            id = R.string.viagem_endereco_simples,
                                            viagemData?.trajeto?.pontoChegada?.logradouro ?: "--",
                                            viagemData?.trajeto?.pontoChegada?.numero ?: 0
                                        )
                                    )
                                    Polyline(
                                        points = state.routePoints,
                                        color = AzulRota,
                                        width = 15f
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}