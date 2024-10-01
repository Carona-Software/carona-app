package com.example.caronaapp.features.historico_viagens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaSombra
import com.example.caronaapp.ui.theme.Filtro
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.utils.layout.CustomItemCard

data class Viagem(
    val partida: String,
    val chegada: String,
    val data: String,
    val hora: String,
)

@Composable
fun HistoricoViagensScreen(navController: NavController) {
    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.historico),
                        color = Azul,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(12.dp),
                                clip = false, // Deixar o conteúdo sem sombra no topo
                                ambientColor = CinzaSombra,
                                spotColor = Cinza90
                            )
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
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

                val viagens = listOf(
                    Viagem(
                        partida = "São Paulo, SP",
                        chegada = "Campinas, SP",
                        data = "16/09/2024",
                        hora = "16:00h",
                    ),
                    Viagem(
                        partida = "São Paulo, SP",
                        chegada = "Campinas, SP",
                        data = "16/09/2024",
                        hora = "16:00h",
                    ),
                )

                LazyColumn(
//                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    items(items = viagens) { viagem ->
                        ViagemCard(
                            partida = viagem.partida,
                            chegada = viagem.chegada,
                            data = viagem.data,
                            hora = viagem.hora
                        ) {}
                    }
                }
            }
        }
    }
}

@Composable
fun ViagemCard(
    partida: String,
    chegada: String,
    data: String,
    hora: String,
    navigate: () -> Unit
) {
    CustomItemCard {
        Row(
            modifier = Modifier.clickable { navigate() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.viagem),
                contentDescription = "Viagem",
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 4.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = PontoPartida,
                        contentDescription = "Partida",
                        tint = Azul,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = partida,
                        color = Azul,
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Localizacao,
                        contentDescription = "Partida",
                        tint = Azul,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = chegada,
                        color = Azul,
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$data - $hora",
                    color = Cinza90,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }

}

@Preview
@Composable
fun HistoricoViagensScreenPreview() {
    HistoricoViagensScreen(navController = rememberNavController())
}