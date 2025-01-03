package com.example.caronaapp.presentation.screens.avaliacoes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.presentation.view_models.AvaliacoesViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun AvaliacoesScreen(
    navController: NavController,
    viewModel: AvaliacoesViewModel = koinViewModel()
) {
    val avaliacoes by viewModel.avaliacoes.collectAsState()
    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(CinzaF5)
            ) {
                TopBarTitle(
                    navController = navController,
                    title = stringResource(id = R.string.avaliacoes),
                    backGround = CinzaF5
                )

                if (isLoadingScreen) {
                    LoadingScreen(backGround = CinzaF5)
                } else {
                    if (avaliacoes != null) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(items = avaliacoes!!.toList()) { avaliacao ->
                                AvaliacaoCard(
                                    nome = avaliacao.avaliador.nome,
                                    fotoUrl = avaliacao.avaliador.fotoUrl,
                                    isFotoValida = avaliacao.avaliador.isFotoValida,
                                    data = avaliacao.dataInDate,
                                    notaFinal = avaliacao.notaMedia,
                                    comentario = avaliacao.comentario
                                )
                            }
                        }
                    } else {
                        NoResultsComponent(
                            text = stringResource(id = R.string.sem_conteudo_avaliacoes)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AvaliacaoCard(
    nome: String,
    fotoUrl: String,
    isFotoValida: Boolean,
    data: LocalDate,
    notaFinal: Double,
    comentario: String
) {
    CustomItemCard(verticalAlignment = Alignment.Top) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            if (isFotoValida) {
                CustomAsyncImage(fotoUrl = fotoUrl, modifier = Modifier.size(52.dp))
            } else {
                CustomDefaultImage(modifier = Modifier.size(52.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.Top
            ) {
                Column( // nome e data
                    modifier = Modifier.fillMaxWidth(0.75f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = nome,
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatDate(data),
                        color = Cinza90,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row( // avaliação
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrela",
                        tint = Amarelo,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = notaFinal.toString(),
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text( // comentário
                text = comentario,
                color = Azul,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AvaliacoesScreenPreview() {
    CaronaAppTheme {
        AvaliacoesScreen(rememberNavController())
    }
}