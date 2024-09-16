package com.example.caronaapp.meu_perfil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.layout.BottomNavBar
import com.example.caronaapp.layout.CriterioFeedback
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8

class MeuPerfil : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaronaAppTheme {
                MeuPerfilScreen()
            }
        }
    }
}

@Composable
fun MeuPerfilScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar() }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.foto_gustavo),
                        contentDescription = "Minha foto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Gustavo Medeiros Silva",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Azul,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

//                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .scale(1.2f),
                    color = CinzaE8,
                    thickness = 2.dp
                )

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
                                imageVector = Icons.Default.Star,
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) { // funcionalidades de Meu Perfil
                        MeuPerfilTopic(
                            icon = Icons.Default.NotificationsNone,
                            label = stringResource(id = R.string.notificacoes)
                        ) {}
                        Spacer(modifier = Modifier.height(8.dp))
                        MeuPerfilTopic(
                            icon = Icons.Default.RateReview,
                            label = stringResource(id = R.string.avaliacoes)
                        ) {}
                        Spacer(modifier = Modifier.height(8.dp))
                        MeuPerfilTopic(
                            icon = Icons.Default.Loyalty,
                            label = stringResource(id = R.string.fidelizados)
                        ) {}
                        Spacer(modifier = Modifier.height(8.dp))
                        MeuPerfilTopic(
                            icon = Icons.Default.DirectionsCar,
                            label = stringResource(id = R.string.carros)
                        ) {}
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .scale(1.2f),
                        color = CinzaE8,
                        thickness = 8.dp
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) { // column de dados de cadastro
                        Text(
                            text = stringResource(id = R.string.dados_pessoais),
                            color = Azul,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.width(24.dp))

                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_nome),
                            valor = "Gustavo Medeiros Silva",
                            editavel = true
                        ) {}
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_email),
                            valor = "gustavo@carona.com",
                            editavel = true
                        ) {}
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_data_nascimento),
                            valor = "10/06/2003",
                            editavel = true
                        ) {}
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_cpf),
                            valor = "123.456.789-00",
                            editavel = false
                        ) {}
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.perfil),
                            valor = "Motorista",
                            editavel = false
                        ) {}
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_genero),
                            valor = "Masculino",
                            editavel = false
                        ) {}
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.endereco),
                                color = Azul,
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.fillMaxWidth(0.92f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = "Navegar",
                                tint = Cinza90,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MeuPerfilTopic(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Azul,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Azul,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth(0.92f)
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = "Navegar",
            tint = Cinza90,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ItemDadosPessoais(
    label: String,
    valor: String,
    editavel: Boolean,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(if (editavel) 0.92f else 1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                color = Azul,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = valor,
                color = Azul,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (editavel) {
            IconButton(onClick = { onEditClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Navegar",
                    tint = Cinza90,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun MeuPerfilScreenPreview() {
    CaronaAppTheme {
        MeuPerfilScreen()
    }
}