package com.example.caronaapp.features.viagens

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
fun ViagensScreen(navController: NavController) {
    var pontoPartida by remember { mutableStateOf("") }
    var pontoChegada by remember { mutableStateOf("") }
    var dia by remember { mutableStateOf("") }

    CaronaAppTheme {
        Scaffold() { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(AzulMensagem),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewViagensScreen() {
    CaronaAppTheme {
        val navController = rememberNavController()
        ViagensScreen(navController)
    }
}