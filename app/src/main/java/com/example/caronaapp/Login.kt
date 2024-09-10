package com.example.caronaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caronaapp.esqueci_senha.EsqueciSenhaEmail
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme

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

@OptIn(ExperimentalMaterial3Api::class)
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
                Text(
                    text = "Email",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Azul,
                    modifier = Modifier.padding(bottom = 4.dp)
                )


                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            BorderStroke(2.dp, Azul),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    textStyle = TextStyle(color = Azul),
                    placeholder = { Text(text = "email@email.com") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Text(
                    text = "Senha",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Azul,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                TextField(
                    value = senha,
                    onValueChange = { senha = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            BorderStroke(2.dp, Azul),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    textStyle = TextStyle(color = Azul),
                    placeholder = { Text(text = "***********") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
//                val nextPag = Intent(contexto)
//                nextPag.putExtra("email", email)
//                nextPag.putExtra("senha", senha)
//
//                contexto.startActivity(nextPag)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Amarelo),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        text = "Entrar",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    val esqueciSenha = Intent(contexto, EsqueciSenhaEmail::class.java)

                    contexto.startActivity(esqueciSenha)
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Esqueceu a senha?",
                        color = Azul,
                        fontSize = 24.sp,
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Divider(color = Color.Gray, thickness = 1.5.dp)

                Spacer(modifier = Modifier.height(26.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Não possui conta?", color = Azul)
                    TextButton(onClick = {
                        val cadastro = Intent(contexto, Cadastro::class.java)

                        contexto.startActivity(cadastro)
                    }) {
                        Text(
                            text = "Cadastre-se",
                            color = Azul,
                            fontSize = 24.sp
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