package com.example.caronaapp.features.carros

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.data.Carro
import com.example.caronaapp.layout.CardButton
import com.example.caronaapp.layout.TopBarTitle
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.VermelhoExcluir

@Composable
fun CarrosScreen(navController: NavController) {
    @Composable
    fun returnCarro(cor: String): Painter {
        return when (cor) {
            "Amarelo" -> painterResource(id = R.drawable.carro_amarelo)
            "Branco" -> painterResource(id = R.drawable.carro_branco)
            "Cinza" -> painterResource(id = R.drawable.carro_cinza)
            "Laranja" -> painterResource(id = R.drawable.carro_laranja)
            "Marrom" -> painterResource(id = R.drawable.carro_marrom)
            "Prata" -> painterResource(id = R.drawable.carro_prata)
            "Preto" -> painterResource(id = R.drawable.carro_preto)
            "Roxo" -> painterResource(id = R.drawable.carro_roxo)
            "Verde" -> painterResource(id = R.drawable.carro_verde)
            "Vermelho" -> painterResource(id = R.drawable.carro_vermelho)
            else -> painterResource(id = R.drawable.carro_vinho)
        }
    }

    CaronaAppTheme {
        Scaffold(
            topBar = {
                TopBarTitle(
                    navController = navController,
                    title = stringResource(id = R.string.carros)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CinzaF5)
                    .padding(innerPadding)
                    .padding(top = 16.dp),
            ) {
                val carros = listOf(
                    Carro(id = 1, marca = "Fiat", modelo = "Mobi", placa = "GJB5A12", cor = "Preto"),
                    Carro(
                        id = 2,
                        marca = "Chevrolet",
                        modelo = "Onix",
                        placa = "YAB7L04",
                        cor = "Vinho"
                    ),
                    Carro(id = 3, marca = "Honda", modelo = "Fit", placa = "AOC3G83", cor = "Prata"),
                )

                carros.forEach { carro ->
                    CarroCard(
                        id = carro.id ?: -1,
                        marca = carro.marca,
                        modelo = carro.modelo,
                        placa = carro.placa,
                        carroImg = returnCarro(cor = carro.cor)
                    )
                }
            }
        }
    }
}

@Composable
fun CarroCard(
    id: Int,
    marca: String,
    modelo: String,
    placa: String,
    carroImg: Painter
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = carroImg,
                contentDescription = "Carro",
                modifier = Modifier
                    .width(110.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column( // carro e placa
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "$marca $modelo",
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = placa,
                        color = Cinza90,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) { // botões
                CardButton(label = "Excluir", backGround = VermelhoExcluir) {}
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(label = "Editar", backGround = Azul) {}
            }
        }
    }
}

@Preview
@Composable
fun CarrosScreenPreview() {
    val navController = rememberNavController()
    CarrosScreen(navController = navController)
}