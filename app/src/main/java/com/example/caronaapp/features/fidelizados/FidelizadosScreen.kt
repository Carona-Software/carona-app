package com.example.caronaapp.features.fidelizados

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.Estrela
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.TopBarTitle

data class Fidelizado(
    val fotoUser: Painter,
    val nome: String,
    val notaGeral: Double,
)

@Composable
fun FidelizadosScreen(navController: NavController) {
    var isRemoveFidelizadoDialogOpened by remember { mutableStateOf(false) }
    var removeFidelizado by remember { mutableStateOf<Fidelizado?>(null) }

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(CinzaF5)
            ) {
                TopBarTitle(
                    navController = navController,
                    backGround = CinzaF5,
                    title = stringResource(id = R.string.fidelizados)
                )

                val fidelizados = listOf(
                    Fidelizado(
                        fotoUser = painterResource(id = R.mipmap.user_default),
                        nome = "Matheus Alves",
                        notaGeral = 4.1
                    ),
                    Fidelizado(
                        fotoUser = painterResource(id = R.mipmap.user_default),
                        nome = "Lucas Arantes",
                        notaGeral = 4.4
                    ),
                )

                LazyColumn {
                    items(items = fidelizados) { fidelizado ->
                        FidelizadoCard(
                            fotoUser = fidelizado.fotoUser,
                            nome = fidelizado.nome,
                            notaGeral = fidelizado.notaGeral,
                            onRemoveButton = {
                                removeFidelizado = fidelizado
                                isRemoveFidelizadoDialogOpened = true
                            }) {
                            navController.navigate("chat")
                        }
                    }
                }

                if (isRemoveFidelizadoDialogOpened) {
                    CustomDialog(
                        onDismissRequest = { isRemoveFidelizadoDialogOpened = false },
                        content = {
                            val message = stringResource(
                                id = R.string.message_confirmation_remove_fidelizado,
                                removeFidelizado!!.nome,
                            )

                            // Estilizar o texto para deixar em negrito apenas uma parte da string
                            val annotatedString = buildAnnotatedString {
                                val nomeIndex = message.indexOf(removeFidelizado!!.nome)

                                append(message.substring(0, nomeIndex))

                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(removeFidelizado!!.nome)
                                }

                                append(
                                    message.substring(
                                        nomeIndex + removeFidelizado!!.nome.length
                                    )
                                )
                            }

                            Text(
                                text = annotatedString,
                                color = Azul,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        },
                        saveButtonLabel = stringResource(id = R.string.label_button_excluir),
                        saveButtonColor = VermelhoExcluir
                    ) {
                        removeFidelizado = null
                        isRemoveFidelizadoDialogOpened = false
                    }
                }
            }
        }
    }
}

@Composable
fun FidelizadoCard(
    fotoUser: Painter,
    nome: String,
    notaGeral: Double,
    onRemoveButton: () -> Unit,
    onConversarButton: () -> Unit,
) {
    CustomItemCard {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = fotoUser,
                contentDescription = null,
                modifier = Modifier
                    .width(68.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column( // carro e placa
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = nome,
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Estrela,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Amarelo
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (notaGeral > 0.0) notaGeral.toString() else "--",
                            color = Cinza90,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) { // botões
                CardButton(
                    label = stringResource(id = R.string.label_button_remover),
                    backGround = VermelhoExcluir
                ) { onRemoveButton() }
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(
                    label = stringResource(id = R.string.label_button_conversar),
                    backGround = Azul
                ) { onConversarButton() }
            }
        }
    }
}

@Preview
@Composable
fun FidelizadosScreenPreview() {
    FidelizadosScreen(navController = rememberNavController())
}