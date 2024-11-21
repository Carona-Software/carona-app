package com.example.caronaapp.presentation.screens.carros

import android.widget.Toast
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.presentation.view_models.CarrosViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Chevron
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.Circulo
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.utils.functions.returnCorCarro
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun CarrosScreen(
    navController: NavController,
    viewModel: CarrosViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.isError, key2 = state.isSuccess) {
        if (state.isError) {
            Toast.makeText(
                context,
                state.errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            delay(300)

        }

        if (state.isSuccess) {
            Toast.makeText(
                context,
                state.successMessage,
                Toast.LENGTH_SHORT
            ).show()

            delay(300)
            viewModel.setIsSuccessToFalse()
        }

    }

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

                if (isLoadingScreen) {
                    LoadingScreen(backGround = CinzaF5)
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(CinzaF5)
                            .padding(bottom = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (state.carros != null) {
                            LazyColumn {
                                items(items = state.carros?.toList() ?: emptyList()) { carro ->
                                    CarroCard(
                                        carroData = carro,
                                        onDeleteButton = { viewModel.onDeleteClick(carro) },
                                        onEditButton = { viewModel.onEditClick(carro) }
                                    )
                                }
                            }
                        } else {
                            NoResultsComponent(
                                text = stringResource(id = R.string.sem_conteudo_carros),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                        ) {
                            ButtonAction(label = stringResource(id = R.string.novo_carro)) {
                                viewModel.onCreateClick()
                            }
                        }
                    }
                }

                when {
                    state.isDeleteDialogOpened -> {
                        CustomDialog(
                            onDismissRequest = { viewModel.onDismissDeleteDialog() },
                            content = {
                                val message = stringResource(
                                    id = R.string.message_confirmation_delete_carro,
                                    state.deleteCarroData?.marca ?: "",
                                    state.deleteCarroData?.modelo ?: ""
                                )

                                // Estilizar o texto para deixar em negrito apenas uma parte da string
                                val annotatedString = buildAnnotatedString {
                                    val marcaIndex =
                                        message.indexOf(state.deleteCarroData?.marca ?: "")
                                    val modeloIndex =
                                        message.indexOf(state.deleteCarroData?.modelo ?: "")

                                    append(message.substring(0, marcaIndex))

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(state.deleteCarroData?.marca ?: "")
                                    }

                                    append(
                                        message.substring(
                                            marcaIndex + (state.deleteCarroData?.marca?.length
                                                ?: 0),
                                            modeloIndex
                                        )
                                    )

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(state.deleteCarroData?.modelo ?: "")
                                    }

                                    append(
                                        message.substring(
                                            modeloIndex + (state.deleteCarroData?.modelo?.length
                                                ?: 0)
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
                            viewModel.handleDeleteCarro()
                        }
                    }

                    state.isEditDialogOpened -> {
                        CarroInfoDialog(
                            onDismissRequest = { viewModel.onDismissEditDialog() },
                            buttonLabel = stringResource(id = R.string.label_button_salvar),
                            carroData = state.editCarroData,
                            onChangeEvent = {
                                viewModel.onChangeEvent(
                                    field = it,
                                    isEditCarro = true
                                )
                            },
                            marcasData = state.marcasCarroData,
                            modelosData = state.modelosCarroData,
                            cores = state.coresCarro,
                            onMarcasDropdownClick = { viewModel.onMarcasDropdownClick() },
                            onModelosDropdownClick = { viewModel.onModelosDropdownClick() },
                            onCoresDropdownClick = { viewModel.onCoresDropdownClick() },
                            isMarcasExpanded = state.isMarcasDropdownExpanded,
                            isModelosExpanded = state.isModelosDropdownExpanded,
                            isCoresExpanded = state.isCoresDropdownExpanded,
                            onMarcasDismissRequest = { viewModel.onDismissMarcasDropdown() },
                            onModelosDismissRequest = { viewModel.onDismissModelosDropdown() },
                            onCoresDismissRequest = { viewModel.onDismissCoresDropdown() },
                            onSaveClick = { viewModel.handleEditCarro() }
                        )
                    }

                    state.isCreateDialogOpened -> {
                        CarroInfoDialog(
                            onDismissRequest = { viewModel.onDismissCreateDialog() },
                            buttonLabel = stringResource(id = R.string.label_button_salvar),
                            carroData = state.createCarroData,
                            onChangeEvent = {
                                viewModel.onChangeEvent(
                                    field = it,
                                    isEditCarro = false
                                )
                            },
                            marcasData = state.marcasCarroData,
                            modelosData = state.modelosCarroData,
                            cores = state.coresCarro,
                            onMarcasDropdownClick = { viewModel.onMarcasDropdownClick() },
                            onModelosDropdownClick = { viewModel.onModelosDropdownClick() },
                            onCoresDropdownClick = { viewModel.onCoresDropdownClick() },
                            isMarcasExpanded = state.isMarcasDropdownExpanded,
                            isModelosExpanded = state.isModelosDropdownExpanded,
                            isCoresExpanded = state.isCoresDropdownExpanded,
                            onMarcasDismissRequest = { viewModel.onDismissMarcasDropdown() },
                            onModelosDismissRequest = { viewModel.onDismissModelosDropdown() },
                            onCoresDismissRequest = { viewModel.onDismissCoresDropdown() },
                            onSaveClick = { viewModel.handleCreateCarro() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CarroCard(
    carroData: CarroListagemDto,
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
                painter = returnCorCarro(cor = carroData.cor),
                contentDescription = "Carro",
                modifier = Modifier.width(110.dp)
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
                        text = stringResource(
                            id = R.string.carro_marca_modelo,
                            carroData.marca,
                            carroData.modelo
                        ),
                        color = Azul,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = carroData.placa,
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
    marcasData: List<String> = emptyList(),
    modelosData: List<String> = emptyList(),
    cores: List<CorCarro>,
    onMarcasDropdownClick: () -> Unit,
    onModelosDropdownClick: () -> Unit,
    onCoresDropdownClick: () -> Unit,
    isMarcasExpanded: Boolean,
    isModelosExpanded: Boolean,
    isCoresExpanded: Boolean,
    onMarcasDismissRequest: () -> Unit,
    onModelosDismissRequest: () -> Unit,
    onCoresDismissRequest: () -> Unit,
    onChangeEvent: (CarroField) -> Unit,
    onSaveClick: () -> Unit
) {
    CustomDialog(
        onDismissRequest = { onDismissRequest() },
        content = {
            Column {
                // Marca
                Column {
                    InputField(
                        label = stringResource(id = R.string.label_marca),
                        value = carroData.marca,
                        enabled = false,
                        endIcon = Chevron,
                        onIconClick = { onMarcasDropdownClick() },
                        modifier = Modifier.clickable { onMarcasDropdownClick() }
                    )
                    Box {
                        DropdownMenu(
                            expanded = isMarcasExpanded,
                            onDismissRequest = { onMarcasDismissRequest() },
                            modifier = Modifier
                                .background(Color.White)
                                .heightIn(max = 160.dp)
                        ) {
                            marcasData.map { marca ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = marca,
                                            color = Azul,
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    },
                                    onClick = { onChangeEvent(CarroField.Marca(marca)) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Modelo
                Column {
                    InputField(
                        label = stringResource(id = R.string.label_modelo),
                        value = carroData.modelo,
                        enabled = false,
                        endIcon = Chevron,
                        onIconClick = { onModelosDropdownClick() },
                        modifier = Modifier.clickable { onModelosDropdownClick() }
                    )
                    Box {
                        DropdownMenu(
                            expanded = isModelosExpanded,
                            onDismissRequest = { onModelosDismissRequest() },
                            modifier = Modifier
                                .background(Color.White)
                                .heightIn(max = 160.dp)
                        ) {
                            modelosData.map { modelo ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = modelo,
                                            color = Azul,
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    },
                                    onClick = { onChangeEvent(CarroField.Modelo(modelo)) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Cor
                Column {
                    InputField(
                        label = stringResource(id = R.string.label_cor),
                        value = carroData.cor,
                        enabled = false,
                        endIcon = Chevron,
                        onIconClick = { onCoresDropdownClick() },
                        modifier = Modifier.clickable { onCoresDropdownClick() }
                    )
                    Box {
                        DropdownMenu(
                            expanded = isCoresExpanded,
                            onDismissRequest = { onCoresDismissRequest() },
                            modifier = Modifier
                                .background(Color.White)
                                .heightIn(max = 160.dp)
                        ) {
                            cores.map { cor ->
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Circulo,
                                            contentDescription = cor.label,
                                            tint = cor.cor,
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = cor.label,
                                            color = Azul,
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    },
                                    onClick = { onChangeEvent(CarroField.Cor(cor.label)) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Placa
                InputField(
                    label = stringResource(id = R.string.label_placa),
                    value = carroData.placa,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
                ) {
                    if (it.length < 8) {
                        onChangeEvent(CarroField.Placa(it))
                    }
                }
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