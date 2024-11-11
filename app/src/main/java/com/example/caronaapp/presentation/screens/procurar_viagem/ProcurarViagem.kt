package com.example.caronaapp.presentation.screens.procurar_viagem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import com.example.caronaapp.presentation.view_models.ProcurarViagemViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.utils.formatDate
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CustomCard
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.example.caronaapp.utils.layout.InputField
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun ProcurarViagemScreen(
    navController: NavController,
    viewModel: ProcurarViagemViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val dateDialogState = rememberMaterialDialogState()

    val state by viewModel.procurarViagemState.collectAsState()

    val isDropdownPartidaOpened by viewModel.isDropdownPartidaOpened.collectAsState()
    val isDropdownChegadaOpened by viewModel.isDropdownChegadaOpened.collectAsState()

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                DropdownEnderecoResult(
                                    inputLabel = stringResource(id = R.string.label_ponto_de_partida),
                                    inputValue = state.pontoPartida,
                                    startIcon = PontoPartida,
                                    onChangeEvent = {
                                        viewModel.onChangeEvent(
                                            ProcurarViagemField.PontoPartida(it)
                                        )
                                    },
                                    isDropdownExpanded = isDropdownPartidaOpened,
                                    results = state.resultsPontoPartida,
                                    onDismissDropdown = { viewModel.onDismissDropdownPartida() },
                                    onDropdownItemClick = { viewModel.onDropDownPartidaClick(it) }
                                )

                                DropdownEnderecoResult(
                                    inputLabel = stringResource(id = R.string.label_ponto_de_chegada),
                                    inputValue = state.pontoChegada,
                                    startIcon = Localizacao,
                                    onChangeEvent = {
                                        viewModel.onChangeEvent(
                                            ProcurarViagemField.PontoChegada(it)
                                        )
                                    },
                                    isDropdownExpanded = isDropdownChegadaOpened,
                                    results = state.resultsPontoChegada,
                                    onDismissDropdown = { viewModel.onDismissDropdownChegada() },
                                    onDropdownItemClick = { viewModel.onDropDownChegadaClick(it) }
                                )

                                InputField(
                                    label = stringResource(id = R.string.label_dia),
                                    value = formatDate(state.data),
                                    startIcon = Calendario,
                                    onIconClick = {
                                        dateDialogState.show()
                                    },
                                    enabled = false
                                )

                                CustomDatePickerDialog(
                                    dialogState = dateDialogState,
                                    allowedDateValidator = {
                                        !it.isBefore(LocalDate.now())
                                    }) { novaData ->
                                    viewModel.onChangeEvent(ProcurarViagemField.Data(novaData))
                                }
                            }
                            Spacer(modifier = Modifier.height(28.dp))
                            ButtonAction(label = stringResource(id = R.string.procurar)) {
                                navController.navigate("viagens")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownEnderecoResult(
    inputLabel: String,
    inputValue: String,
    startIcon: ImageVector,
    onChangeEvent: (String) -> Unit,
    isDropdownExpanded: Boolean,
    results: List<GeocodeResponse.Result>,
    onDismissDropdown: () -> Unit,
    onDropdownItemClick: (GeocodeResponse.Result) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        InputField(
            label = inputLabel,
            value = inputValue,
            startIcon = startIcon,
            buttonIconEnabled = false
        ) {
            onChangeEvent(it)
        }
        Spacer(modifier = Modifier.height(2.dp))

        if (results.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { onDismissDropdown() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            border = BorderStroke(1.dp, Cinza90),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(Color.White)
                ) {
                    results.forEach { result ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = result.formatted_address,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Azul,
                                    textAlign = TextAlign.Start
                                )
                            },
                            onClick = { onDropdownItemClick(result) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewProcurarViagemScreen() {
    CaronaAppTheme {
        ProcurarViagemScreen(rememberNavController())
    }
}