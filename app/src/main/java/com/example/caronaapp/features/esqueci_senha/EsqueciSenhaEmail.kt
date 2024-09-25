package com.example.caronaapp.features.esqueci_senha

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun EsqueciSenhaEmailScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var emailInvalido by remember { mutableStateOf(false) }

    fun onEmailChange(it: String) {
        email = it
        emailInvalido = email.isNotBlank() && (
                !email.contains("@") ||
                        !email.contains(".") ||
                        email.length < 8)
    }

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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    alignment = Alignment.TopStart,
                    painter = painterResource(id = R.mipmap.arrow_left),
                    contentDescription = "seta para voltar",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                )
            }
            Image(
                painter = painterResource(id = R.mipmap.img_esqueci_senha),
                contentDescription = "img esqueci senha",
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
                    .padding(horizontal = 10.dp)
            )
            Text(
                text = "Esqueceu a senha?",
                color = Azul,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(25.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Não se preocupe. Enviaremos um código ao seu email cadastrado para alterar a senha.",
                    style = MaterialTheme.typography.titleSmall,
                    color = Azul,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                InputField(
                    label = stringResource(id = R.string.label_email),
                    value = email,
                    supportingText = stringResource(id = R.string.input_message_error_email),
                    isError = emailInvalido,
                ) {
                    onEmailChange(it)
                }
                ButtonAction(label = stringResource(id = R.string.label_button_enviar_codigo)) {
                    navController.navigate("esqueci-senha/codigo/$email")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EsqueciSenhaEmailScreenPreview() {
    CaronaAppTheme {
        val navController = rememberNavController()
        EsqueciSenhaEmailScreen(navController)
    }
}