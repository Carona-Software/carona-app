package com.example.caronaapp.features.procurar_viagem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
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
import com.example.caronaapp.R
import com.example.caronaapp.layout.BottomNavBar
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.CustomCard
import com.example.caronaapp.layout.InputField
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.CaronaAppTheme

class ProcurarViagem : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProcurarViagemScreen()
        }
    }
}

@Composable
fun ProcurarViagemScreen() {
    var pontoPartida by remember { mutableStateOf("") }
    var pontoChegada by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar() }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .background(AzulMensagem)
                    .fillMaxSize()
                    .padding(
                        top = 48.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_viagem),
                    contentDescription = "Motorista",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.2f)
                )
                CustomCard(modifier = Modifier.padding(innerPadding)) {
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
                            startIcon = Icons.Default.MyLocation
                        ) {
                            pontoPartida = it
                        }
                        InputField(
                            label = stringResource(id = R.string.label_ponto_de_chegada),
                            value = pontoChegada,
                            startIcon = Icons.Default.LocationOn
                        ) {
                            pontoChegada = it
                        }
                        InputField(
                            label = stringResource(id = R.string.label_dia),
                            value = dia,
                            startIcon = Icons.Default.CalendarMonth
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

@Preview(showSystemUi = true)
@Composable
fun PreviewProcurarViagemScreen() {
    CaronaAppTheme {
        ProcurarViagemScreen()
    }
}