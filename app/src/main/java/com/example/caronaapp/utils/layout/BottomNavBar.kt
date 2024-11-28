package com.example.caronaapp.utils.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulInativo
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Chat
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.Pessoa
import com.example.caronaapp.ui.theme.Oferecer
import com.example.caronaapp.ui.theme.Procurar
import com.example.caronaapp.ui.theme.Viagem

class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val isCurrent: Boolean,
    val allowedProfile: String,
    val navigate: () -> Unit
)

@Composable
fun BottomNavBar(navController: NavController, perfilUser: String) {
    val currentScreen = navController.currentBackStackEntry?.destination?.route

    val bottomBarItens = listOf(
        BottomBarItem(
            label = stringResource(id = R.string.procurar),
            icon = Procurar,
            isCurrent = currentScreen == "viagens/procurar",
            allowedProfile = "PASSAGEIRO",
            navigate = { navController.navigate("viagens/procurar") }
        ),
        BottomBarItem(
            label = stringResource(id = R.string.oferecer),
            icon = Oferecer,
            isCurrent = currentScreen == "viagens/oferecer",
            allowedProfile = "MOTORISTA",
            navigate = { navController.navigate("viagens/oferecer") }
        ),
        BottomBarItem(
            label = stringResource(id = R.string.chat),
            icon = Chat,
            isCurrent = currentScreen == "chat",
            allowedProfile = "PASSAGEIRO/MOTORISTA",
            navigate = { navController.navigate("chat") }
        ),
        BottomBarItem(
            label = stringResource(id = R.string.viagens),
            icon = Viagem,
            isCurrent = currentScreen == "viagens/historico",
            allowedProfile = "PASSAGEIRO/MOTORISTA",
            navigate = { navController.navigate("viagens/historico") }
        ),
        BottomBarItem(
            label = stringResource(id = R.string.perfil),
            icon = Pessoa,
            isCurrent = currentScreen == "meu-perfil",
            allowedProfile = "PASSAGEIRO/MOTORISTA",
            navigate = { navController.navigate("meu-perfil") }
        ),
    )

    CaronaAppTheme {
        Column(
            modifier = Modifier
                .height(76.dp)
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                color = CinzaE8,
                thickness = 1.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                bottomBarItens.map { item ->
                    if (item.allowedProfile.uppercase().contains(perfilUser.uppercase())) {
                        Column(
                            modifier = Modifier
                                .clip(CircleShape)
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(8.dp)
                                .clickable { item.navigate() },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (item.isCurrent) Azul else AzulInativo,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.label,
                                color = if (item.isCurrent) Azul else AzulInativo,
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

