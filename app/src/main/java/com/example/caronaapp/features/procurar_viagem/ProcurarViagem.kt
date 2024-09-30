package com.example.caronaapp.features.procurar_viagem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CustomCard
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida

@Composable
fun ProcurarViagemScreen(navController: NavController) {
    var pontoPartida by remember { mutableStateOf("") }
    var pontoChegada by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }

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
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            InputField(
                                label = stringResource(id = R.string.label_ponto_de_partida),
                                value = pontoPartida,
                                startIcon = PontoPartida,
                                buttonIconEnabled = false
                            ) {
                                pontoPartida = it
                            }
                            InputField(
                                label = stringResource(id = R.string.label_ponto_de_chegada),
                                value = pontoChegada,
                                startIcon = Localizacao,
                                buttonIconEnabled = false
                            ) {
                                pontoChegada = it
                            }
                            InputField(
                                label = stringResource(id = R.string.label_dia),
                                value = dia,
                                startIcon = Calendario
                            ) {
                                dia = it
                            }

                            ButtonAction(label = stringResource(id = R.string.procurar)) {

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
        val navController = rememberNavController()
        ProcurarViagemScreen(navController)
    }
}