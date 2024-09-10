package com.example.caronaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.cadastro.CadastroEndereco
import com.example.caronaapp.cadastro.CadastroFoto
import com.example.caronaapp.cadastro.CadastroPessoais
import com.example.caronaapp.cadastro.CadastroPerfil
import com.example.caronaapp.cadastro.CadastroSenha
import com.example.caronaapp.layout.CustomCard
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulStepCadastro
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaD9

class Cadastro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroScreen()
        }
    }
}

class CadastroStep(
    val label: String,
    val etapa: Int,
    val onclick: () -> Unit,
)

@Composable
fun CadastroScreen() {
    val contexto = LocalContext.current
    var etapaAtual by remember { mutableStateOf(1) }

//    val telasEtapas = listOf(
//        CadastroPessoaisScreen(),
//        CadastroPerfilScreen(),
//        CadastroEnderecoScreen(),
//        CadastroFotoScreen(),
//        CadastroSenhaScreen()
//    )

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
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 16.dp,
                    bottom = 8.dp
                )
                .height(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CadastroStep(
                label = stringResource(id = R.string.pessoais),
                etapa = 1,
                etapaAtual = etapaAtual,
                onClick = { etapaAtual = 1 }
            )
            CadastroStep(
                label = stringResource(id = R.string.perfil),
                etapa = 2,
                etapaAtual = etapaAtual,
                onClick = { etapaAtual = 2 }
            )
            CadastroStep(
                label = stringResource(id = R.string.endereco),
                etapa = 3,
                etapaAtual = etapaAtual,
                onClick = { etapaAtual = 3 }
            )
            CadastroStep(
                label = stringResource(id = R.string.foto),
                etapa = 4,
                etapaAtual = etapaAtual,
                onClick = { etapaAtual = 4 }
            )
            CadastroStep(
                label = stringResource(id = R.string.senha),
                etapa = 5,
                etapaAtual = etapaAtual,
                onClick = { etapaAtual = 5 }
            )
        }

            Spacer(modifier = Modifier.height(8.dp))

        CustomCard(
            shadowElevation = 24f, // Intensidade da sombra
            shadowOffsetY = 16f, // Desloca a sombra mais para cima
        ) {
            when (etapaAtual) {
                1 -> CadastroPessoais(onClick = { etapaAtual = 2 })
                2 -> CadastroPerfil(onClick = { etapaAtual = 3 })
                3 -> CadastroEndereco(onClick = { etapaAtual = 4 })
                4 -> CadastroFoto(onClick = { etapaAtual = 5 })
                else -> CadastroSenha(onClick = {
                    val login = Intent(contexto, Login::class.java)
                    contexto.startActivity(login)
                })
            }
        }

    }
}

@Composable
fun CadastroStep(
    label: String,
    etapa: Int,
    etapaAtual: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(76.dp)
            .padding(
                start = 4.dp,
                end = 4.dp
            )
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = (
                    if (etapa <= etapaAtual) Azul
                    else AzulStepCadastro
                    ),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (etapa <= etapaAtual) Azul
                    else CinzaD9
                )
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