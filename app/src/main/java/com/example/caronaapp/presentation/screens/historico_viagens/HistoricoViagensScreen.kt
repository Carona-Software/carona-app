package com.example.caronaapp.presentation.screens.historico_viagens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.presentation.view_models.HistoricoViagensViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.CinzaSombra
import com.example.caronaapp.ui.theme.Filtro
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.functions.formatTime
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

data class DropdownMenuViagem(
    val label: String,
    val data: LocalDate?
)

@Composable
fun HistoricoViagensScreen(
    navController: NavController,
    viewModel: HistoricoViagensViewModel = koinViewModel()
) {
    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()
    val perfilUser by viewModel.perfilUser.collectAsState()
    val viagens by viewModel.viagens.collectAsState()
    val viagensFiltradas by viewModel.viagensFiltradas.collectAsState()
    val isExpanded by viewModel.isExpanded.collectAsState()
    val currentFilterOption by viewModel.currentFilterOption.collectAsState()

    val itensDropdownMenu = listOf(
        DropdownMenuViagem(
            stringResource(id = R.string.todas),
            LocalDate.of(1900, 1, 1)
        ),
        DropdownMenuViagem(
            stringResource(id = R.string.hoje),
            LocalDate.now()
        ),
        DropdownMenuViagem(
            stringResource(id = R.string.filtro_data_ultima_semana),
            LocalDate.now().minusDays(7)
        ),
        DropdownMenuViagem(
            stringResource(id = R.string.filtro_data_ultimo_mes),
            LocalDate.now().minusDays(30)
        ),
    )

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController, perfilUser) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(CinzaF5)
                    .fillMaxSize()
            ) {
                if (isLoadingScreen) {
                    LoadingScreen(backGround = CinzaF5)
                } else {
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

                        if (viagens != null) {
                            Box {
                                Button(
                                    onClick = { viewModel.expandDropdownMenu() },
                                    modifier = Modifier
                                        .shadow(
                                            elevation = 4.dp,
                                            shape = RoundedCornerShape(12.dp),
                                            clip = false, // Deixar o conteúdo sem sombra no topo
                                            ambientColor = CinzaSombra,
                                            spotColor = Cinza90
                                        )
                                        .clip(RoundedCornerShape(8.dp))
                                        .height(40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ),
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 0.dp
                                    ),
                                ) {
                                    Text(
                                        text = currentFilterOption,
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

                                DropdownMenu(
                                    expanded = isExpanded,
                                    onDismissRequest = { viewModel.onDismissRequest() },
                                    modifier = Modifier.background(Color.White)
                                ) {
                                    itensDropdownMenu.forEach { item ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = item.label,
                                                    color = Azul,
                                                    style = MaterialTheme.typography.bodySmall,
                                                )
                                            },
                                            onClick = {
                                                viewModel.onMenuItemClick(
                                                    item.label,
                                                    item.data!!
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (viagensFiltradas.isEmpty()) {
                        NoResultsComponent(text = stringResource(id = R.string.sem_conteudo_viagem))
                    } else {
                        LazyColumn {
                            items(items = viagensFiltradas.toList()) { viagem ->
                                ViagemCard(viagemData = viagem) {
                                    navController.navigate("viagens/detalhes/${viagem.id}/null/null")
                                }
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
                        text = stringResource(
                            id = R.string.viagem_cidade_uf,
                            viagemData.trajeto.pontoPartida.cidade,
                            viagemData.trajeto.pontoPartida.uf,
                        ),
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
                        text = stringResource(
                            id = R.string.viagem_cidade_uf,
                            viagemData.trajeto.pontoChegada.cidade,
                            viagemData.trajeto.pontoChegada.uf,
                        ),
                        color = Azul,
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(
                        id = R.string.viagem_data_hora,
                        formatDate(viagemData.dataInDate),
                        formatTime(viagemData.horarioPartidaInTime)
                    ),
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