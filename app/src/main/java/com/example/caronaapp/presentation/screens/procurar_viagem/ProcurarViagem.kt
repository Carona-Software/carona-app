package com.example.caronaapp.presentation.screens.procurar_viagem

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.presentation.view_models.ProcurarViagemViewModel
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CustomCard
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.example.caronaapp.utils.layout.DropdownEnderecoResult
import com.example.caronaapp.utils.layout.InputField
import com.google.gson.Gson
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun ProcurarViagemScreen(
    navController: NavController,
    viewModel: ProcurarViagemViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val perfilUser by viewModel.perfilUser.collectAsState()

    val dateDialogState = rememberMaterialDialogState()

    val state by viewModel.state.collectAsState()
    val viagemProcuraDto by viewModel.viagemProcuraDto.collectAsState()

    val isDropdownPartidaOpened by viewModel.isDropdownPartidaOpened.collectAsState()
    val isDropdownChegadaOpened by viewModel.isDropdownChegadaOpened.collectAsState()

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
                                    },
                                    selectedDate = state.data,
                                    onDateChange = { novaData ->
                                        viewModel.onChangeEvent(ProcurarViagemField.Data(novaData))
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(28.dp))
                            ButtonAction(label = stringResource(id = R.string.procurar)) {
                                if (
                                    viagemProcuraDto.data != "" &&
                                    viagemProcuraDto.pontoPartida != null &&
                                    viagemProcuraDto.pontoChegada != null
                                ) {
                                    val viagemToString = Gson().toJson(viagemProcuraDto)
                                    navController.navigate(
                                        "viagens/${viagemToString}/${state.pontoPartida}/${state.pontoChegada}"
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Preencha todos os campos",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
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