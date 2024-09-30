package com.example.caronaapp.features.carros

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.caronaapp.data.Carro
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.InputField

@Composable
fun CarrosScreen(navController: NavController) {
    val context = LocalContext.current

    @Composable
    fun returnCarro(cor: String): Painter {
        return when (cor) {
            "Amarelo" -> painterResource(id = R.drawable.carro_amarelo)
            "Branco" -> painterResource(id = R.drawable.carro_branco)
            "Cinza" -> painterResource(id = R.drawable.carro_cinza)
            "Laranja" -> painterResource(id = R.drawable.carro_laranja)
            "Marrom" -> painterResource(id = R.drawable.carro_marrom)
            "Prata" -> painterResource(id = R.drawable.carro_prata)
            "Preto" -> painterResource(id = R.drawable.carro_preto)
            "Roxo" -> painterResource(id = R.drawable.carro_roxo)
            "Verde" -> painterResource(id = R.drawable.carro_verde)
            "Vermelho" -> painterResource(id = R.drawable.carro_vermelho)
            else -> painterResource(id = R.drawable.carro_vinho)
        }
    }

    var isNovoCarroDialogOpened by remember { mutableStateOf(false) }
    var isEditCarroDialogOpened by remember { mutableStateOf(false) }
    var isDeleteCarroDialogOpened by remember { mutableStateOf(false) }

    val novoCarro = Carro()
    var editCarro by remember { mutableStateOf<Carro?>(null) }
    var deleteCarro by remember { mutableStateOf<Carro?>(null) }

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(CinzaF5)
            ) {
                val carros = listOf(
                    Carro(
                        id = 1,
                        marca = "Fiat",
                        modelo = "Mobi",
                        placa = "GJB5A12",
                        cor = "Preto"
                    ),
                    Carro(
                        id = 2,
                        marca = "Chevrolet",
                        modelo = "Onix",
                        placa = "YAB7L04",
                        cor = "Vinho"
                    ),
                    Carro(
                        id = 3,
                        marca = "Honda",
                        modelo = "Fit",
                        placa = "AOC3G83",
                        cor = "Prata"
                    ),
                )

                TopBarTitle(
                    navController = navController,
                    title = stringResource(id = R.string.carros),
                    backGround = CinzaF5
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CinzaF5)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn {
                        items(items = carros) { carro ->
                            CarroCard(
                                id = carro.id ?: -1,
                                marca = carro.marca,
                                modelo = carro.modelo,
                                placa = carro.placa,
                                carroImg = returnCarro(cor = carro.cor),
                                onDeleteButton = {
                                    deleteCarro = carro
                                    isDeleteCarroDialogOpened = true
                                },
                                onEditButton = {
                                    editCarro = carro
                                    isEditCarroDialogOpened = true
                                }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                    ) {
                        ButtonAction(label = stringResource(id = R.string.novo_carro)) {
                            isNovoCarroDialogOpened = true
                        }
                    }
                }

                if (isDeleteCarroDialogOpened) {
                    CustomDialog(
                        onDismissRequest = { isDeleteCarroDialogOpened = false },
                        content = {
                            val message = stringResource(
                                id = R.string.message_confirmation_delete_carro,
                                deleteCarro!!.marca,
                                deleteCarro!!.modelo
                            )

                            // Estilizar o texto para deixar em negrito apenas uma parte da string
                            val annotatedString = buildAnnotatedString {
                                val marcaIndex = message.indexOf(deleteCarro!!.marca)
                                val modeloIndex = message.indexOf(deleteCarro!!.modelo)

                                append(message.substring(0, marcaIndex))

                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(deleteCarro!!.marca)
                                }

                                append(
                                    message.substring(
                                        marcaIndex + deleteCarro!!.marca.length,
                                        modeloIndex
                                    )
                                )

                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(deleteCarro!!.modelo)
                                }

                                append(message.substring(modeloIndex + deleteCarro!!.modelo.length))
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
                        deleteCarro = null
                        isDeleteCarroDialogOpened = false
                    }
                }

                if (isEditCarroDialogOpened) {
                    CarroInfoDialog(
                        onDismissRequest = { isEditCarroDialogOpened = false },
                        buttonLabel = stringResource(id = R.string.label_button_editar),
                        carroData = editCarro!!,
                        onMarcaChange = { marca ->
                            editCarro!!.marca = marca
                        },
                        onModeloChange = { modelo ->
                            editCarro!!.modelo = modelo
                        },
                        onCorChange = { cor ->
                            editCarro!!.cor = cor
                        },
                        onPlacaChange = { placa ->
                            editCarro!!.placa = placa
                        }
                    ) {
                        Toast.makeText(
                            context,
                            "Carro atualizado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        editCarro = null
                        isEditCarroDialogOpened = false
                    }
                }

                if (isNovoCarroDialogOpened) {
                    CarroInfoDialog(
                        onDismissRequest = { isNovoCarroDialogOpened = false },
                        buttonLabel = stringResource(id = R.string.label_button_salvar),
                        carroData = novoCarro,
                        onMarcaChange = { marca ->
                            novoCarro.marca = marca
                        },
                        onModeloChange = { modelo ->
                            novoCarro.modelo = modelo
                        },
                        onCorChange = { cor ->
                            novoCarro.cor = cor
                        },
                        onPlacaChange = { placa ->
                            novoCarro.placa = placa
                        }
                    ) {
                        Toast.makeText(
                            context,
                            "Carro cadastrado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        isNovoCarroDialogOpened = false
                    }
                }
            }
        }
    }
}

@Composable
fun CarroCard(
    id: Int,
    marca: String,
    modelo: String,
    placa: String,
    carroImg: Painter,
    onDeleteButton: () -> Unit,
    onEditButton: () -> Unit,
) {
    CustomItemCard {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = carroImg,
                contentDescription = "Carro",
                modifier = Modifier
                    .width(110.dp)
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
                        text = "$marca $modelo",
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = placa,
                        color = Cinza90,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) { // botões
                CardButton(
                    label = stringResource(id = R.string.label_button_excluir),
                    backGround = VermelhoExcluir
                ) { onDeleteButton() }
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(
                    label = stringResource(id = R.string.label_button_editar),
                    backGround = Azul
                ) { onEditButton() }
            }
        }
    }
}

@Composable
fun CarroInfoDialog(
    onDismissRequest: () -> Unit,
    buttonLabel: String,
    carroData: Carro,
    onMarcaChange: (String) -> Unit,
    onModeloChange: (String) -> Unit,
    onCorChange: (String) -> Unit,
    onPlacaChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    CustomDialog(
        onDismissRequest = { onDismissRequest() },
        content = {
            Column {
                InputField(
                    label = stringResource(id = R.string.label_marca),
                    value = carroData.marca
                ) { onMarcaChange(it) }
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = stringResource(id = R.string.label_modelo),
                    value = carroData.modelo
                ) { onModeloChange(it) }
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = stringResource(id = R.string.label_cor),
                    value = carroData.cor
                ) { onCorChange(it) }
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    label = stringResource(id = R.string.label_placa),
                    value = carroData.placa
                ) { onPlacaChange(it) }
            }
        },
        saveButtonLabel = buttonLabel,
        saveButtonColor = Amarelo
    ) { onSaveClick() }
}

@Preview
@Composable
fun CarrosScreenPreview() {
    val navController = rememberNavController()
    CarrosScreen(navController = navController)
}