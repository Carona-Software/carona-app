package com.example.caronaapp.presentation.screens.oferecer_viagem

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.presentation.view_models.OferecerViagemViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.AzulStepCadastro
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaD9
import com.example.caronaapp.ui.theme.SetaEsquerda
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CustomCard
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

class OferecerViagemStepClass(
    val label: String,
    val idEtapa: Int,
)

@Composable
fun OferecerViagemScreen(
    navController: NavController,
    viewModel: OferecerViagemViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val perfilUser by viewModel.perfilUser.collectAsState()
    val generoUser by viewModel.generoUser.collectAsState()

    val etapaAtual by viewModel.etapaAtual.collectAsState()

    val state by viewModel.state.collectAsState()
    val carros by viewModel.carros.collectAsState()

    val stepsOferecerViagem = listOf(
        OferecerViagemStepClass(
            label = stringResource(id = R.string.trajeto),
            idEtapa = 1
        ),
        OferecerViagemStepClass(
            label = stringResource(id = R.string.horario),
            idEtapa = 2
        ),
        OferecerViagemStepClass(
            label = stringResource(id = R.string.detalhes),
            idEtapa = 3
        )
    )

    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val idCreatedViagem by viewModel.idCreatedViagem.collectAsState()

    LaunchedEffect(key1 = isSuccessful, key2 = isError) {
        if (isError) {
            Toast.makeText(context, "Houve um erro ao criar viagem", Toast.LENGTH_SHORT).show()

            delay(300)
            viewModel.setIsErrorToFalse()
        }
        if (isSuccessful) {
            Toast.makeText(context, "Viagem cadastrada com sucesso", Toast.LENGTH_SHORT).show()

            delay(300)
            viewModel.setIsSuccessfulToFalse()
            navController.navigate("viagens/detalhes/$idCreatedViagem")
        }
    }

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController, perfilUser) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(AzulMensagem),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.background_viagem),
                        contentDescription = "Motorista",
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(1.30f)
                    )
                    CustomCard {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                stepsOferecerViagem.forEach { step ->
                                    StepOferecerViagem(
                                        label = step.label,
                                        idEtapa = step.idEtapa,
                                        etapaAtual = etapaAtual
                                    )
                                }
                            }

                            if (etapaAtual > 1) {
                                Row(
                                    modifier = Modifier.padding(top = 24.dp)
                                ) {
                                    IconButton(
                                        onClick = { viewModel.handleBackClick() },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = SetaEsquerda,
                                            contentDescription = "Voltar",
                                            tint = Azul
                                        )
                                    }
                                }
                            }

                            when (etapaAtual) {
                                1 -> OferecerViagemDestino(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    onDismissChegadaDropdown = {},
                                    onDismissPartidaDropdown = {},
                                    state = state,
                                    handleOnChange = { viewModel.onChangeEvent(it) },
                                    handleOnClick = { viewModel.onEnderecoClick(it) }
                                )

                                2 -> OferecerViagemHorario(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    state = state,
                                    handleOnChange = { viewModel.onChangeEvent(it) }
                                )

                                3 -> OferecerViagemDetalhes(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    state = state,
                                    generoUser = generoUser,
                                    handleOnChange = { viewModel.onChangeEvent(it) },
                                    carrosData = carros,
                                    onCarrosDropdownClick = { viewModel.onCarrosDropdownClick() },
                                    onCarroItemClick = { viewModel.onChangeEvent(it) },
                                    onCarrosDismissRequest = { viewModel.onCarrosDismissRequest() }
                                )
                            }

                            ButtonAction(
                                label = if (etapaAtual == 3) stringResource(id = R.string.label_button_finalizar)
                                else stringResource(id = R.string.label_button_proximo),
                                isLoading = isLoading
                            ) {
                                viewModel.handleNextClick()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepOferecerViagem(
    label: String,
    idEtapa: Int,
    etapaAtual: Int
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(96.dp)
            .padding(
                start = 4.dp,
                end = 4.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = (
                    if (idEtapa <= etapaAtual) Azul
                    else AzulStepCadastro
                    ),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (idEtapa <= etapaAtual) Azul
                    else CinzaD9
                )
        ) {}
    }
}


@Preview
@Composable
fun OferecerViagemScreenPreview() {
    OferecerViagemScreen(navController = rememberNavController())
}