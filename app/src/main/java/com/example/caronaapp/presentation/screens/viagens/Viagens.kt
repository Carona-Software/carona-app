package com.example.caronaapp.presentation.screens.viagens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Carro
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaCB
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.CinzaSwitchButton
import com.example.caronaapp.ui.theme.CoracaoPreenchido
import com.example.caronaapp.ui.theme.Filtro
import com.example.caronaapp.ui.theme.LaranjaLonge
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.Mais
import com.example.caronaapp.ui.theme.Menos
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.ui.theme.VerdePerto
import com.example.caronaapp.ui.theme.VerdeSwitchButton
import com.example.caronaapp.ui.theme.VermelhoErro
import com.example.caronaapp.utils.layout.TopBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViagensScreen(navController: NavController) {

    var pontoPartida by remember { mutableStateOf("São Paulo, SP") }
    var pontoChegada by remember { mutableStateOf("Taubaté, SP") }
    var dia by remember { mutableStateOf("") }
    var capacidadePassageiros by remember { mutableStateOf("1") }
    var precoMinimo by remember { mutableStateOf("") }
    var precoMaximo by remember { mutableStateOf("") }
    var apenasMulheres by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CinzaF5)
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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 10.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { showBottomSheet = true },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CinzaF5
                                ),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                                content = {
                                    Row(
                                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.filtrar),
                                            color = Azul,
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Filtro,
                                            contentDescription = null,
                                            tint = Azul,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
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

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    modifier = Modifier,
                    containerColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.filtrar),
                            color = Azul,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Preço mínimo e preço máximo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            PrecoFiltroComponent(
                                label = stringResource(id = R.string.label_preco_minimo),
                                value = precoMinimo,
                                handleOnChange = { precoMinimo = it }
                            )
                            Spacer(modifier = Modifier.width(24.dp))
                            PrecoFiltroComponent(
                                label = stringResource(id = R.string.label_preco_maximo),
                                value = precoMaximo,
                                handleOnChange = { precoMaximo = it }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = stringResource(id = R.string.label_quantidade_passageiros),
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                modifier = Modifier
                                    .height(52.dp)
                                    .border(
                                        border = BorderStroke(2.dp, Azul),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Menos,
                                        contentDescription = "Menos",
                                        tint = Azul,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Text(
                                    text = capacidadePassageiros,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Azul,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Mais,
                                        contentDescription = "Mais",
                                        tint = Azul,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Apenas Mulheres
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.apenas_mulheres),
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = apenasMulheres,
                                onCheckedChange = { apenasMulheres = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    uncheckedThumbColor = Color.White,
                                    checkedTrackColor = VerdeSwitchButton,
                                    uncheckedTrackColor = CinzaSwitchButton,
                                    checkedBorderColor = VerdeSwitchButton,
                                    uncheckedBorderColor = CinzaSwitchButton
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Buttons: Limpar e Aplicar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CinzaF5
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.label_button_limpar),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Azul,
                                )
                            }
                            TextButton(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Azul
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.label_button_aplicar),
                                    style = MaterialTheme.typography.displayLarge,
                                    color = Color.White,
                                )
                            }
                        }
                    }
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
                            Text(
                                text = origem,
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul
                            )
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
                            Text(
                                text = destino,
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul
                            )
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

@Composable
fun PrecoFiltroComponent(
    label: String,
    value: String,
    handleOnChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Azul,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .width(140.dp)
                .height(52.dp)
                .border(
                    border = BorderStroke(2.dp, Azul),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.cifrao_rs),
                style = MaterialTheme.typography.labelMedium,
                color = Azul,
            )
            TextField(
                value = value,
                onValueChange = { handleOnChange(it) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Azul,
                    unfocusedTextColor = Azul,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledPlaceholderColor = Cinza90,
                    disabledTextColor = Azul
                ),
                textStyle = MaterialTheme.typography.headlineMedium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewViagensScreen() {
    CaronaAppTheme {
        ViagensScreen(rememberNavController())
    }
}
