package com.example.caronaapp.esqueci_senha

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caronaapp.R
import com.example.caronaapp.esqueci_senha.ui.theme.CaronaAppTheme
import com.example.caronaapp.layout.InputField
import com.example.caronaapp.ui.theme.Azul

class RedefinirSenha : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaronaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RedefinirSenha(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RedefinirSenha(name: String, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    var senha by remember { mutableStateOf("") }
    var confirmacaoSenha by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 26.dp, vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    alignment = Alignment.TopStart,
                    painter = painterResource(id = R.mipmap.arrow_left),
                    contentDescription = "seta para voltar",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            val secondPage = Intent(
                                contexto, EsqueciSenhaCodigo::class.java
                            )
                            contexto.startActivity(secondPage)
                        },
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Redefinir senha",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = Azul
            )
            Spacer(modifier = Modifier.height(30.dp))


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(id = R.string.a_senha_deve_conter),
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Default.Done, tint = Azul, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.letra_maiuscula),
                            color = Azul,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Default.Done, tint = Azul, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.letra_minuscula),
                            color = Azul,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Default.Done, tint = Azul, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.numero),
                            color = Azul,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Default.Done, tint = Azul, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(id = R.string.caracter_especial),
                            color = Azul,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InputField(
                        label = stringResource(id = R.string.label_senha),
                        value = senha,
                        maxLines = 1,
                        handleChange = { senha = it }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    InputField(
                        label = stringResource(id = R.string.label_confirmacao_senha),
                        value = confirmacaoSenha,
                        maxLines = 1,
                        handleChange = { confirmacaoSenha = it }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    CaronaAppTheme {
        RedefinirSenha("Android")
    }
}