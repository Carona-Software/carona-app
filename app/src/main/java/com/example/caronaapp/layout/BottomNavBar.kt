package com.example.caronaapp.layout

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaE8

class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val navigate: () -> Unit
)

@Composable
fun BottomNavBar() {
    var bottomBarItens = listOf(
        BottomBarItem(
            stringResource(id = R.string.procurar),
            Icons.Default.Search,
            navigate = {}
        ),
        BottomBarItem(
            stringResource(id = R.string.chat),
            Icons.AutoMirrored.Filled.Chat,
            navigate = {}
        ),
        BottomBarItem(
            stringResource(id = R.string.viagens),
            Icons.Default.Map,
            navigate = {}
        ),
        BottomBarItem(
            stringResource(id = R.string.perfil),
            Icons.Default.Person,
            navigate = {}
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
                bottomBarItens.map {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(8.dp)
                            .clickable { },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.label,
                            tint = Azul,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it.label,
                            color = Azul,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewBottomNavBar() {
    CaronaAppTheme {
        BottomNavBar()
    }
}
