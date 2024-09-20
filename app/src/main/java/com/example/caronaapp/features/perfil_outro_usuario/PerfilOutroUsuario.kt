package com.example.caronaapp.features.perfil_outro_usuario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.CriterioFeedback
import com.example.caronaapp.layout.TopBarUser
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.Estrela
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.Viagem

@Composable
fun PerfilOutroUsuarioScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    CaronaAppTheme {
        Scaffold(
            topBar = {
                TopBarUser(navController = navController, fotoUser = null, nome = "Lucas Arantes")
            },
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                Column(
                    // column com scroll
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(scrollState),
                ) {
                    Column( // column de avaliação geral
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.avaliacao_geral),
                            color = Azul,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Estrela,
                                contentDescription = "Estrela",
                                tint = Amarelo,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "4.3",
                                color = Azul,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) { // column de critérios de feedback
                        CriterioFeedback(
                            label = stringResource(id = R.string.dirigibilidade),
                            notaMedia = 4.0,
                            percentualNotaMedia = 0.75f
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CriterioFeedback(
                            label = stringResource(id = R.string.segurança),
                            notaMedia = 4.3,
                            percentualNotaMedia = 0.84f
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CriterioFeedback(
                            label = stringResource(id = R.string.comunicação),
                            notaMedia = 3.2,
                            percentualNotaMedia = 0.66f
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CriterioFeedback(
                            label = stringResource(id = R.string.pontualidade),
                            notaMedia = 4.9,
                            percentualNotaMedia = 0.91f
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .scale(1.2f),
                        color = CinzaE8,
                        thickness = 8.dp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Viagem,
                            contentDescription = "",
                            tint = Azul,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.total_viagens_realizadas, 13),
                            color = Azul,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth(0.92f)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .scale(1.2f),
                        color = CinzaE8,
                        thickness = 8.dp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Localizacao,
                            contentDescription = "",
                            tint = Azul,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "São Paulo, SP",
                            color = Azul,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth(0.92f)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .scale(1.2f),
                        color = CinzaE8,
                        thickness = 8.dp
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.comentarios),
                            color = Azul,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Avaliacao(
                            fotoUser = null,
                            nome = "Gustavo Medeiros",
                            data = "18/09/2024",
                            comentario = "Dirige bem, pontual e respeitoso! Com certeza recomendo!"
                        )

                        HorizontalDivider(
//                            modifier = Modifier
//                                .padding(vertical = 4.dp),
                            color = CinzaE8,
                            thickness = 1.dp
                        )

                        Avaliacao(
                            fotoUser = null,
                            nome = "Gustavo Medeiros",
                            data = "18/09/2024",
                            comentario = "Dirige bem, pontual e respeitoso! Com certeza recomendo!"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    ) {
                        ButtonAction(label = stringResource(id = R.string.label_button_conversar)) {}
                    }

                }
            }
        }
    }
}

@Composable
fun Avaliacao(
    fotoUser: Painter?,
    nome: String,
    data: String,
    comentario: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = fotoUser ?: painterResource(id = R.mipmap.user_default),
                contentDescription = "usuário",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                // nome e data
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = nome,
                    color = Azul,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data,
                    color = Cinza90,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = comentario,
            color = Azul,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview
@Composable
fun PerfilOutroUsuarioScreenPreview() {
    val navController = rememberNavController()
    PerfilOutroUsuarioScreen(navController = navController)
}