package com.example.caronaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caronaapp.esqueci_senha.EsqueciSenhaEmail
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.InputField
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaE8

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaronaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Login(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Login(name: String, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val contexto = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 26.dp, vertical = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CaRona",
                modifier = modifier
                    .padding(top = 55.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Azul
            )

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                InputField(
                    label = stringResource(id = R.string.label_email),
                    value = email,
                ) {
                    email = it
                }
                Spacer(modifier = Modifier.height(25.dp))
                InputField(
                    label = stringResource(id = R.string.senha),
                    value = senha,
                ) {
                    senha = it
                }
                Spacer(modifier = Modifier.height(40.dp))
                ButtonAction(label = stringResource(id = R.string.label_button_entrar)) {

                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    val esqueciSenha = Intent(contexto, EsqueciSenhaEmail::class.java)

                    contexto.startActivity(esqueciSenha)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Esqueceu a senha?",
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                HorizontalDivider(thickness = 1.5.dp, color = CinzaE8)

                Spacer(modifier = Modifier.height(26.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Não possui conta?",
                        color = Azul,
                        style = MaterialTheme.typography.titleSmall
                    )
                    TextButton(onClick = {
                        val cadastro = Intent(contexto, Cadastro::class.java)
                        contexto.startActivity(cadastro)
                    }) {
                        Text(
                            text = "Cadastre-se",
                            color = Azul,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CaronaAppTheme {
        Login("Android")
    }
}