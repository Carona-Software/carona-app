package com.example.caronaapp.features.esqueci_senha

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun EsqueciSenhaCodigoScreen(
    navController: NavController,
    email: String?
) {
    var codigo by remember { mutableStateOf("") }

    CaronaAppTheme {
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
                                navController.popBackStack()
                            },
                    )
                }
                Image(
                    painter = painterResource(id = R.mipmap.img_second_page_esqueci_senha),
                    contentDescription = "img esqueci senha",
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                        .padding(horizontal = 10.dp)
                )
                Text(
                    text = "Verifique sua caixa de entrada",
                    color = Azul,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        append("Digite o código enviado para ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(email)
                        }
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = Azul,
                )
                Spacer(modifier = Modifier.height(35.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(5) { index ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .border(2.dp, color = Azul, RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = codigo.getOrNull(index)?.toString() ?: "",
                                style = TextStyle(
                                    color = Azul,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            )
                        }
                    }
                }

                BasicTextField(
                    value = codigo,
                    onValueChange = {
                        if (it.length <= 6) {
                            codigo = it
                        }
                    },
                    decorationBox = { innerTextField -> }
                )
                Spacer(modifier = Modifier.height(35.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Não recebeu o código?",
                        color = Azul,
                        style = MaterialTheme.typography.titleSmall
                    )
                    TextButton(onClick = { }) {
                        Text(
                            text = "Reenviar",
                            style = MaterialTheme.typography.labelLarge,
                            color = Azul
                        )
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
                Button(
                    onClick = {
                        navController.navigate("esqueci-senha/redefinir")
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
                        text = "Verificar",
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EsqueciSenhaCodigoScreenPreview() {
    CaronaAppTheme {
        val navController = rememberNavController()
        EsqueciSenhaCodigoScreen(navController = navController, email = "null")
    }
}