package com.example.caronaapp.screens.carros

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.utils.carrosFactory
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.view_models.CarrosViewModel

@Composable
fun CarrosScreen(navController: NavController) {
    val context = LocalContext.current

    val viewModel = viewModel<CarrosViewModel>(
        factory = carrosFactory()
    )

    @Composable
    fun returnCorCarro(cor: String): Painter {
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

    val carros = viewModel.carros.collectAsState()

    val isNovoCarroDialogOpened by viewModel.isCreateDialogOpened.collectAsState()
    val isEditCarroDialogOpened by viewModel.isEditDialogOpened.collectAsState()
    val isDeleteCarroDialogOpened by viewModel.isDeleteDialogOpened.collectAsState()

    val novoCarro = CarroCriacaoDto()
    val editCarro by viewModel.editCarro.collectAsState()
    val deleteCarro by viewModel.deleteCarro.collectAsState()

    val isError by viewModel.isError.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    val errorMessage by viewModel.errorMessage.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()

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
                    if (carros.value != null) {
                        LazyColumn {
                            items(items = carros.value!!.toList()) { carro ->
                                CarroCard(
                                    id = carro.id,
                                    marca = carro.marca,
                                    modelo = carro.modelo,
                                    placa = carro.placa,
                                    carroImg = returnCorCarro(cor = carro.cor),
                                    onDeleteButton = { viewModel.onDeleteCLick(carro) },
                                    onEditButton = { viewModel.onEditCLick(carro) }
                                )
                            }
                        }
                    } else {
                        NoResultsComponent(
                            image = painterResource(id = R.drawable.no_result_image),
                            text = stringResource(id = R.string.sem_conteudo_carros),
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                    ) {
                        ButtonAction(label = stringResource(id = R.string.novo_carro)) {
                            viewModel.onCreateCLick()
                        }
                    }
                }

                when {
                    isDeleteCarroDialogOpened -> {
                        CustomDialog(
                            onDismissRequest = { viewModel.onDismissDeleteDialog() },
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
                            viewModel.handleDeleteCarro()
                        }
                    }

                    isEditCarroDialogOpened -> {
                        editCarro?.let { carro ->
                            CarroInfoDialog(
                                onDismissRequest = { viewModel.onDismissEditDialog() },
                                buttonLabel = stringResource(id = R.string.label_button_editar),
                                carroData = carro,
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
                                viewModel.handleEditCarro()
                            }
                        }
                    }

                    isNovoCarroDialogOpened -> {
                        CarroInfoDialog(
                            onDismissRequest = { viewModel.onDismissCreateDialog() },
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
                            viewModel.handleCreateCarro()
                        }
                    }
                }
            }

            when {
                isError -> {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                isSuccess -> {
                    Toast.makeText(
                        context,
                        successMessage,
                        Toast.LENGTH_SHORT
                    ).show()
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
    carroData: CarroCriacaoDto,
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
    CarrosScreen(navController = rememberNavController())
}