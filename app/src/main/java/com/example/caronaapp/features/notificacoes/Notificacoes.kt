package com.example.caronaapp.features.notificacoes

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.Cancelamento
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.NotificacaoNaoLida
import com.example.caronaapp.ui.theme.SolicitacaoCarona

@Composable
fun NotificacoesScreen(navController: NavController) {
    CaronaAppTheme {
        Scaffold(
            topBar = {
                TopBarTitle(
                    navController = navController,
                    title = stringResource(id = R.string.notificacoes),
                    backGround = CinzaF5
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CinzaF5)
                    .padding(innerPadding)
                    .padding(top = 16.dp)
            ) {
                Notificacao(
                    title = stringResource(id = R.string.notification_title_reserva_de_carona),
                    icon = SolicitacaoCarona,
                    message = stringResource(
                        id = R.string.notification_message_reserva_de_carona,
                        "Lucas Arantes", "20/09/2024"
                    )
                ) {}
                Notificacao(
                    title = stringResource(id = R.string.notification_title_nova_mensagem),
                    icon = NotificacaoNaoLida,
                    message = stringResource(
                        id = R.string.notification_message_nova_mensagem,
                        "Lucas Arantes", "20/09/2024"
                    )
                ) {}
                Notificacao(
                    title = stringResource(id = R.string.notification_title_cancelamento),
                    icon = Cancelamento,
                    message = stringResource(
                        id = R.string.notification_message_cancelamento,
                        "Lucas Arantes", "20/09/2024"
                    )
                ) {}
            }
        }
    }
}

@Composable
fun Notificacao(
    title: String,
    icon: ImageVector,
    message: String,
    onVerClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column( // column de icone
            modifier = Modifier
                .padding(12.dp)
                .size(44.dp)
                .clip(CircleShape)
                .background(AzulMensagem),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Azul,
                modifier = Modifier.size(24.dp)
            )
        }

        Column( // column de title e notificação
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row( // row de title e button
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = Azul,
                    style = MaterialTheme.typography.labelLarge,
                )

                Button(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .height(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Amarelo,
                    ),
                    contentPadding = PaddingValues(),
                    onClick = { onVerClick() }
                ) {
                    Text(
                        text = stringResource(id = R.string.ver),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text( // mensagem de notificação
                text = message,
                color = Cinza90,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun NotificacoesScreenPreview() {
    CaronaAppTheme {
        val navController = rememberNavController()
        NotificacoesScreen(navController)
    }
}