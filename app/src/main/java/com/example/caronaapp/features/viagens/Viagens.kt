package com.example.caronaapp.features.viagens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.Carro
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaCB
import com.example.caronaapp.ui.theme.CinzaComboBox
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.CoracaoPreenchido
import com.example.caronaapp.ui.theme.EnviarMensagem
import com.example.caronaapp.ui.theme.LaranjaLonge
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.ui.theme.VerdePerto
import com.example.caronaapp.ui.theme.VermelhoErro
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.utils.layout.TopBarUser

@Composable
fun ViagensScreen(navController: NavController) {

    var pontoPartida by remember { mutableStateOf("São Paulo, SP") }
    var pontoChegada by remember { mutableStateOf("Taubaté, SP") }
    var dia by remember { mutableStateOf("") }

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TopBarTitle(navController = navController, title = "Viagens")

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 16.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CinzaF5),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(start = 5.dp),
                                imageVector = PontoPartida,
                                contentDescription = "Voltar",
                                tint = Azul,
                            )

                            TextField(
                                value = pontoPartida,
                                onValueChange = { pontoPartida = it },
                                placeholder = {
                                    Text(
                                        text = "Ponto de partida",
                                        color = Cinza90,
                                        style = MaterialTheme.typography.displayLarge
                                    )
                                },
                                modifier = Modifier
                                    .background(Color.Transparent),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Azul,
                                    unfocusedTextColor = Azul,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = MaterialTheme.typography.headlineMedium
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 16.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CinzaF5),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(start = 5.dp),
                                imageVector = Localizacao,
                                contentDescription = "Chegada",
                                tint = Azul,
                            )

                            TextField(
                                value = pontoChegada,
                                onValueChange = { pontoChegada = it },
                                placeholder = {
                                    Text(
                                        text = "Ponto de chegada",
                                        color = Cinza90,
                                        style = MaterialTheme.typography.displayLarge
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .background(Color.Transparent),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Azul,
                                    unfocusedTextColor = Azul,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = MaterialTheme.typography.headlineMedium
                            )
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 16.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(start = 5.dp),
                                imageVector = Calendario,
                                contentDescription = "date",
                                tint = Azul,
                            )

                            TextField(
                                value = dia,
                                onValueChange = { dia = it },
                                placeholder = {
                                    Text(
                                        text = "Data",
                                        style = MaterialTheme.typography.displayLarge
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .background(Color.Transparent),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Azul,
                                    unfocusedTextColor = Azul,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = MaterialTheme.typography.headlineMedium
                            )
                        }

                        HorizontalDivider(color = CinzaE8, thickness = 2.dp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    ViagemCard(
                        horarioSaida = "14:00",
                        horarioChegada = "16:00",
                        origem = pontoPartida,
                        destino = pontoChegada,
                        preco = "R$ 37",
                        motorista = "Gustavo Medeiros",
                        avaliacao = 4.3f
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    startIcon: ImageVector,
    buttonIconEnabled: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = label) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun ViagemCard(
    horarioSaida: String,
    horarioChegada: String,
    origem: String,
    destino: String,
    preco: String,
    motorista: String,
    avaliacao: Float
) {
    var isFavorito by remember { mutableStateOf(false) }

    fun setFavorito(valor: Boolean) {
        if (isFavorito == valor) isFavorito = false else isFavorito = true
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = horarioSaida, fontSize = 16.sp,
                            color = Azul, fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = PontoPartida,
                            contentDescription = "Voltar",
                            tint = Azul,
                        )
                        Column {
                            Text(text = origem, fontSize = 14.sp, color = Azul)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(24.dp)
                                        .background(CinzaCB),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize(),
                                        imageVector = Carro,
                                        contentDescription = "Voltar",
                                        tint = Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(24.dp)
                                        .background(CinzaCB),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize(),
                                        imageVector = Carro,
                                        contentDescription = "Voltar",
                                        tint = Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(24.dp)
                                        .background(LaranjaLonge),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize(),
                                        imageVector = Carro,
                                        contentDescription = "Voltar",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = horarioChegada, fontSize = 16.sp,
                            color = Azul, fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Localizacao,
                            contentDescription = "Voltar",
                            tint = Azul,
                        )
                        Column {
                            Text(text = destino, fontSize = 14.sp, color = Azul)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(24.dp)
                                        .background(VerdePerto),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize(),
                                        imageVector = Carro,
                                        contentDescription = "Voltar",
                                        tint = Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(24.dp)
                                        .background(CinzaCB),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize(),
                                        imageVector = Carro,
                                        contentDescription = "Voltar",
                                        tint = Color.White,
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(24.dp)
                                        .background(CinzaCB),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize(),
                                        imageVector = Carro,
                                        contentDescription = "Voltar",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    text = preco,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Azul
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                color = CinzaE8,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.mipmap.foto_gustavo),
                        contentDescription = "Foto do motorista",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop

                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        Text(
                            text = motorista,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Azul
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "★", color = Color(0xFFFFC107))
                            Text(
                                text = "$avaliacao",
                                fontSize = 16.sp,
                                color = Cinza90,
                                modifier = Modifier.padding(start = 5.dp)

                            )
                        }
                    }
                }
                IconButton(onClick = {
                    setFavorito(true)
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CinzaF5),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = CoracaoPreenchido,
                            modifier = Modifier.size(26.dp),
                            tint = VermelhoErro,
                            contentDescription = "Favoritar"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewViagensScreen() {
    CaronaAppTheme {
        val navController = rememberNavController()
        ViagensScreen(navController)
    }
}
