package com.example.caronaapp.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.presentation.view_models.LoginViewModel
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.InputField
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    val context = LocalContext.current

    val userLoginDto = viewModel.userLoginDto.collectAsState()
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()
    val isError by viewModel.isError.collectAsState()

    LaunchedEffect(key1 = isLoginSuccessful, key2 = isError) {
        if (isLoginSuccessful) {
            Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

            delay(300)

            viewModel.setLoginSuccessfulToFalse()
            navController.navigate("meu-perfil")
        }

        if (isError) {
            Toast.makeText(context, "Erro ao realizar login", Toast.LENGTH_SHORT).show()

            delay(300)
            viewModel.setIsErrorToFalse()
        }
    }

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
            Row(
                modifier = Modifier
                    .padding(top = 55.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.logo_carona),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Carona",
                    style = MaterialTheme.typography.titleLarge,
                    color = Azul
                )

            }

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                InputField(
                    label = stringResource(id = R.string.label_email),
                    value = userLoginDto.value.email,
                ) {
                    viewModel.onEmailChange(it)
                }
                Spacer(modifier = Modifier.height(25.dp))
                InputField(
                    label = stringResource(id = R.string.senha),
                    value = userLoginDto.value.senha,
                    visualTransformation = PasswordVisualTransformation()
                ) {
                    viewModel.onSenhaChange(it)
                }
                Spacer(modifier = Modifier.height(40.dp))
                ButtonAction(label = stringResource(id = R.string.label_button_entrar)) {
//                    navController.navigate("meu-perfil")
                    viewModel.onLoginClick()
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    navController.navigate("esqueci-senha")
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
                        navController.navigate("cadastro")
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
fun LoginScreenPreview() {
    CaronaAppTheme {
        LoginScreen(rememberNavController())
    }
}