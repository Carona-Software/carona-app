package com.example.caronaapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.CustomCard
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun CadastroScreen() {
    Column(
        modifier = Modifier
            .background(Color(0xFFF1F1F1))
            .fillMaxSize()
            .padding(
                top = 48.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.cadastro),
            color = Azul,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CadastroStep(stringResource(id = R.string.pessoais))
            CadastroStep(stringResource(id = R.string.perfil))
            CadastroStep(stringResource(id = R.string.endereco))
            CadastroStep(stringResource(id = R.string.foto))
            CadastroStep(stringResource(id = R.string.senha))
        }

        Spacer(modifier = Modifier.height(8.dp))

        CustomCard(
            shadowElevation = 16f, // Intensidade da sombra
            shadowOffsetY = 18f, // Desloca a sombra mais para cima
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Nome")}
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Email")}
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "CPF")}
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Data de Nascimento")}
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Data de Nascimento")}
                )
                ButtonAction(handleClick = {})
            }
        }

    }
}



@Composable
fun CadastroStep(label: String) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(76.dp),
//                .background(Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Azul,
            style = MaterialTheme.typography.displayMedium
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Azul)
        ) {}
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCadastroScreen() {
    CaronaAppTheme {
        CadastroScreen()
    }
}