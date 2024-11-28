package com.example.caronaapp.presentation.screens.viagens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import com.example.caronaapp.presentation.view_models.ViagensViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.AmareloMedio
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Carro
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaCB
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.EstrelaPreenchida
import com.example.caronaapp.ui.theme.Filtro
import com.example.caronaapp.ui.theme.LaranjaLonge
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.ui.theme.VerdePerto
import com.example.caronaapp.utils.functions.formatTime
import com.example.caronaapp.utils.layout.ApenasMulheresSwitch
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.PrecoFieldComponent
import com.example.caronaapp.utils.layout.QtdPassageirosField
import com.example.caronaapp.utils.layout.TopBarTitle
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViagensScreen(
    navController: NavController,
    viagem: ViagemProcuraDto,
    pontoPartida: String,
    pontoDestino: String,
    viewModel: ViagensViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.setViagemDto(
            viagem = viagem,
            pontoPartida = pontoPartida,
            pontoDestino = pontoDestino
        )
    }

    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()
    val viagens by viewModel.viagens.collectAsState()
    val state by viewModel.state.collectAsState()
    val perfilUser by viewModel.perfilUser.collectAsState()

    val sheetState = rememberModalBottomSheetState()

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CinzaF5)
                ) {
                    TopBarTitle(navController = navController, title = "Viagens")

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
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
                                    text = state.pontoPartida,
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
                                    text = state.pontoDestino,
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
                                    modifier = Modifier,
                                    imageVector = Calendario,
                                    contentDescription = "date",
                                    tint = Azul,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = state.data,
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Azul
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.showBottomSheet() },
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .height(36.dp)
                                .align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CinzaF5
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Row(
                                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.filtrar),
                                    color = Azul,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Filtro,
                                    contentDescription = null,
                                    tint = Azul,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        HorizontalDivider(
                            color = CinzaE8,
                            thickness = 1.dp,
                            modifier = Modifier.scale(1.2f)
                        )
                    }

                    if (isLoadingScreen) {
                        LoadingScreen(backGround = CinzaF5)
                    } else {
                        Spacer(modifier = Modifier.height(10.dp))

                        if (viagens == null) {
                            NoResultsComponent(text = stringResource(id = R.string.sem_conteudo_viagem))
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(items = viagens!!.toList()) { viagem ->
                                    ViagemCard(
                                        viagemData = viagem,
                                        navigate = {
                                            navController.navigate(
                                                "viagens/detalhes/${viagem.id}/${viagem.distanciaPartida}/${viagem.distanciaDestino}"
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (state.showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.onDismissBottomSheet() },
                    sheetState = sheetState,
                    modifier = Modifier,
                    containerColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.filtrar),
                            color = Azul,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Preço mínimo e preço máximo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            PrecoFieldComponent(
                                label = stringResource(id = R.string.label_preco_minimo),
                                value = if (state.precoMinimo == 0.0) "" else state.precoMinimo.toString(),
                                handleOnChange = {
                                    viewModel.handleOnChange(
                                        ViagensField.PrecoMinimo(it.toDouble())
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(24.dp))
                            PrecoFieldComponent(
                                label = stringResource(id = R.string.label_preco_maximo),
                                value = if (state.precoMaximo == 0.0) "" else state.precoMaximo.toString(),
                                handleOnChange = {
                                    viewModel.handleOnChange(
                                        ViagensField.PrecoMaximo(it.toDouble())
                                    )
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Capacidade de passageiros
                        QtdPassageirosField(
                            value = state.capacidadePassageiros.toString(),
                            handleAddQnt = {
                                if (state.capacidadePassageiros < 4) {
                                    viewModel.handleOnChange(
                                        ViagensField.CapacidadePassageiros(
                                            state.capacidadePassageiros + 1
                                        )
                                    )
                                }
                            },
                            handleRemoveQnt = {
                                if (state.capacidadePassageiros > 1) {
                                    viewModel.handleOnChange(
                                        ViagensField.CapacidadePassageiros(
                                            state.capacidadePassageiros - 1
                                        )
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Apenas Mulheres
                        if (perfilUser.uppercase() == "FEMININO") {
                            ApenasMulheresSwitch(
                                checked = state.apenasMulheres,
                                onCheckedChange = {
                                    viewModel.handleOnChange(ViagensField.ApenasMulheres(it))
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Buttons: Limpar e Aplicar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = { viewModel.clearFilters() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CinzaF5
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.label_button_limpar),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Azul,
                                )
                            }
                            TextButton(
                                onClick = { viewModel.handleFilterViagens() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Azul
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.label_button_aplicar),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViagemCard(
    viagemData: ViagemListagemDto,
    navigate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { navigate() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = formatTime(viagemData.horarioPartidaInTime),
                            style = MaterialTheme.typography.displayLarge,
                            color = Azul,
                        )
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = PontoPartida,
                            contentDescription = "Voltar",
                            tint = Azul,
                        )
                        Column {
                            Text(
                                text = stringResource(
                                    id = R.string.viagem_cidade_uf,
                                    viagemData.trajeto.pontoPartida.cidade,
                                    viagemData.trajeto.pontoPartida.uf
                                ),
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            DistanciaIndicator(distancia = viagemData.distanciaPartida)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = formatTime(viagemData.horarioChegadaInTime),
                            style = MaterialTheme.typography.displayLarge,
                            color = Azul,
                        )
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Localizacao,
                            contentDescription = null,
                            tint = Azul,
                        )
                        Column {
                            Text(
                                text = stringResource(
                                    id = R.string.viagem_cidade_uf,
                                    viagemData.trajeto.pontoChegada.cidade,
                                    viagemData.trajeto.pontoChegada.uf
                                ),
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            DistanciaIndicator(distancia = viagemData.distanciaDestino)
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.cifrao_rs),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Azul
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = viagemData.preco.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Azul
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                color = CinzaE8,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (viagemData.motorista.isFotoValida) {
                        CustomAsyncImage(
                            modifier = Modifier.size(48.dp),
                            fotoUrl = viagemData.motorista.fotoUrl
                        )
                    } else {
                        CustomDefaultImage(modifier = Modifier.size(48.dp))
                    }

                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = viagemData.motorista.nome,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Azul
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = EstrelaPreenchida,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Amarelo
                            )
                            Text(
                                text = if (viagemData.motorista.notaGeral == 0.0) "--"
                                else "${viagemData.motorista.notaGeral}",
                                fontSize = 16.sp,
                                color = Cinza90,
                                modifier = Modifier.padding(start = 5.dp)

                            )
                        }
                    }
                }
//                IconButton(onClick = {
//                    setFavorito(true)
//                }) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(CinzaF5),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = CoracaoPreenchido,
//                            modifier = Modifier.size(26.dp),
//                            tint = VermelhoErro,
//                            contentDescription = "Favoritar"
//                        )
//                    }
//                }
            }
        }
    }
}

@Composable
fun DistanciaIndicator(
    distancia: Double
) {
    Row {
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp)
                .background(if (distancia <= 2.0) VerdePerto else CinzaCB),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                imageVector = Carro,
                contentDescription = null,
                tint = Color.White,
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp)
                .background(if (distancia > 3.0 && distancia <= 10.0) AmareloMedio else CinzaCB),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                imageVector = Carro,
                contentDescription = null,
                tint = Color.White,
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp)
                .background(if (distancia > 10.0) LaranjaLonge else CinzaCB),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                imageVector = Carro,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewViagensScreen() {
    CaronaAppTheme {
        ViagensScreen(
            navController = rememberNavController(),
            viagem = ViagemProcuraDto(),
            pontoPartida = "São Paulo, SP",
            pontoDestino = "Taubaté, SP",
        )
    }
}
