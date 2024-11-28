package com.example.caronaapp.presentation.screens.meu_perfil

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.caronaapp.R
import com.example.caronaapp.presentation.view_models.MeuPerfilViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Avaliacao
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Carro
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.Editar
import com.example.caronaapp.ui.theme.EstrelaPreenchida
import com.example.caronaapp.ui.theme.Fidelizacao
import com.example.caronaapp.ui.theme.Procurar
import com.example.caronaapp.ui.theme.SetaDireita
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.utils.functions.formatCpf
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.CriterioFeedback
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.masks.CepVisualTransformation
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun MeuPerfilScreen(
    navController: NavController,
    viewModel: MeuPerfilViewModel = koinViewModel()
) {
    val perfilUser by viewModel.perfilUser.collectAsState()
    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()
    val userData by viewModel.userData.collectAsState()
    val meuPerfilState by viewModel.meuPerfilState.collectAsState()
    val isFotoValida by viewModel.isFotoValida.collectAsState()
    val avaliacoesCriterioUser by viewModel.avaliacoesCriterioUser.collectAsState()
    val validations by viewModel.validations.collectAsState()

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val dateDialogState = rememberMaterialDialogState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { novaFoto ->
        viewModel.onChangeEvent(MeuPerfilField.Foto(novaFoto!!))
    }

    fun logout() {
        viewModel.onDismissModalClick("logout")

        viewModel.clearDataStore()

        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController, perfilUser) }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                if (isLoadingScreen) {
                    LoadingScreen()
                } else {
                    if (userData == null) {
                        NoResultsComponent(text = stringResource(id = R.string.sem_conteudo_perfil))
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isFotoValida) {
                                CustomAsyncImage(
                                    fotoUrl = meuPerfilState.fotoAtual,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clickable { viewModel.onOpenModalClick("foto") }
                                )
                            } else {
                                CustomDefaultImage(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clickable { viewModel.onOpenModalClick("foto") }
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                text = userData!!.nome,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Azul,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .scale(1.2f),
                            color = CinzaE8,
                            thickness = 1.dp
                        )

                        Column(
                            // column com scroll
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .verticalScroll(scrollState),
                        ) {
                            Column( // column de avaliação geral
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.avaliacao_geral),
                                    color = Azul,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = EstrelaPreenchida,
                                        contentDescription = "Estrela",
                                        tint = Amarelo,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (userData!!.notaMedia == 0.0) "--" else userData!!.notaMedia.toString(),
                                        color = Azul,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) { // column de critérios de feedback
                                if (userData!!.perfil.uppercase() == "MOTORISTA") {
                                    CriterioFeedback(
                                        label = stringResource(id = R.string.dirigibilidade),
                                        notaMedia = avaliacoesCriterioUser.dirigibilidade.notaMedia,
                                        percentualNotaMedia = avaliacoesCriterioUser.dirigibilidade.percentual
                                    )
                                } else {
                                    CriterioFeedback(
                                        label = stringResource(id = R.string.comportamento),
                                        notaMedia = avaliacoesCriterioUser.comportamento.notaMedia,
                                        percentualNotaMedia = avaliacoesCriterioUser.comportamento.percentual
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                CriterioFeedback(
                                    label = stringResource(id = R.string.seguranca),
                                    notaMedia = avaliacoesCriterioUser.seguranca.notaMedia,
                                    percentualNotaMedia = avaliacoesCriterioUser.seguranca.percentual
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                CriterioFeedback(
                                    label = stringResource(id = R.string.comunicacao),
                                    notaMedia = avaliacoesCriterioUser.comunicacao.notaMedia,
                                    percentualNotaMedia = avaliacoesCriterioUser.comunicacao.percentual
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                CriterioFeedback(
                                    label = stringResource(id = R.string.pontualidade),
                                    notaMedia = avaliacoesCriterioUser.pontualidade.notaMedia,
                                    percentualNotaMedia = avaliacoesCriterioUser.pontualidade.percentual
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .scale(1.2f),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) { // funcionalidades de Meu Perfil
//                            MeuPerfilTopic(
//                                icon = Notificacao,
//                                label = stringResource(id = R.string.notificacoes)
//                            ) {
//                                navController.navigate("meu-perfil/notificacoes")
//                            }
                                Spacer(modifier = Modifier.height(8.dp))
                                MeuPerfilTopic(
                                    icon = Avaliacao,
                                    label = stringResource(id = R.string.avaliacoes)
                                ) {
                                    navController.navigate("meu-perfil/avaliacoes")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                MeuPerfilTopic(
                                    icon = Fidelizacao,
                                    label = stringResource(id = R.string.fidelizados)
                                ) {
                                    navController.navigate("meu-perfil/fidelizados")
                                }
                                if (userData!!.perfil.uppercase() == "MOTORISTA") {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    MeuPerfilTopic(
                                        icon = Carro,
                                        label = stringResource(id = R.string.carros)
                                    ) {
                                        navController.navigate("meu-perfil/carros")
                                    }
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .scale(1.2f),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) { // column de dados de cadastro
                                Text(
                                    text = stringResource(id = R.string.dados_pessoais),
                                    color = Azul,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 20.dp)
                                )

                                ItemDadosPessoais(
                                    label = stringResource(id = R.string.label_nome),
                                    valor = userData!!.nome,
                                    editavel = true
                                ) {
                                    viewModel.onOpenModalClick("nome")
                                }
                                ItemDadosPessoais(
                                    label = stringResource(id = R.string.label_email),
                                    valor = userData!!.email,
                                    editavel = true,
                                ) {
                                    viewModel.onOpenModalClick("email")
                                }
                                ItemDadosPessoais(
                                    label = stringResource(id = R.string.label_data_nascimento),
                                    valor = formatDate(userData!!.dataNascimentoDate),
                                    editavel = true
                                ) {
                                    viewModel.onOpenModalClick("data de nascimento")
                                }
                                ItemDadosPessoais(
                                    label = stringResource(id = R.string.label_cpf),
                                    valor = formatCpf(userData!!.cpf),
                                    editavel = false
                                )
                                ItemDadosPessoais(
                                    label = stringResource(id = R.string.perfil),
                                    valor = userData!!.perfil.lowercase()
                                        .replaceFirstChar { it.uppercase() },
                                    editavel = false
                                )
                                ItemDadosPessoais(
                                    label = stringResource(id = R.string.label_genero),
                                    valor = userData!!.genero.lowercase()
                                        .replaceFirstChar { it.uppercase() },
                                    editavel = false
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.endereco),
                                        color = Azul,
                                        style = MaterialTheme.typography.displayLarge,
                                        modifier = Modifier.fillMaxWidth(0.92f)
                                    )
                                    IconButton(onClick = { viewModel.onOpenModalClick("endereço") }) {
                                        Icon(
                                            imageVector = Editar,
                                            contentDescription = "Editar",
                                            tint = Cinza90,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 20.dp)
                                    .scale(1.2f),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(onClick = {
                                    viewModel.onOpenModalClick("logout")
                                }) {
                                    Text(
                                        text = stringResource(id = R.string.label_button_sair),
                                        color = VermelhoExcluir,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }

                            // Modal
                            when {
                                meuPerfilState.isNomeDialogEnabled -> {
                                    CustomDialog(
                                        saveButtonColor = Amarelo,
                                        saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                        onDismissRequest = { viewModel.onDismissModalClick("nome") },
                                        content = {
                                            InputField(
                                                label = stringResource(id = R.string.label_nome),
                                                value = meuPerfilState.nome,
                                                handleChange = {
                                                    viewModel.onChangeEvent(
                                                        MeuPerfilField.Nome(
                                                            it
                                                        )
                                                    )
                                                },
                                                supportingText = stringResource(id = R.string.input_message_error_nome),
                                                isError = validations.isNomeInvalido,
                                            )
                                        },
                                        onSaveClick = {
                                            viewModel.onDismissModalClick("nome")
//                                        if (novoNome.isNotBlank() && novoNome.length < 5) {
//                                            nomeInvalido = true
//                                        } else {
//                                            Toast.makeText(
//                                                context,
//                                                "Nome atualizado com sucesso",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                            viewModel.onDismissModalClick("nome")
//                                        }
                                        }
                                    )
                                }

                                meuPerfilState.isEmailDialogEnabled -> {
                                    CustomDialog(
                                        saveButtonColor = Amarelo,
                                        saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                        onDismissRequest = { viewModel.onDismissModalClick("email") },
                                        content = {
                                            InputField(
                                                label = stringResource(id = R.string.label_email),
                                                value = meuPerfilState.email,
                                                handleChange = {
                                                    viewModel.onChangeEvent(
                                                        MeuPerfilField.Email(
                                                            it
                                                        )
                                                    )
                                                },
                                                supportingText = stringResource(id = R.string.input_message_error_email),
                                                isError = validations.isEmailInvalido,
                                            )
                                        },
                                        onSaveClick = {
                                            viewModel.onDismissModalClick("email")
//                                        if (!isEmailValid(novoEmail)) {
//                                            emailInvalido = true
//                                        } else {
//                                            Toast.makeText(
//                                                context,
//                                                "Email atualizado com sucesso",
//                                                Toast.LENGTH_SHORT
//                                            ).show()
//                                            viewModel.onDismissModalClick("email")
//                                        }
                                        }
                                    )
                                }

                                meuPerfilState.isDataNascimentoDialogEnabled -> {
                                    CustomDialog(
                                        saveButtonColor = Amarelo,
                                        saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                        onDismissRequest = { viewModel.onDismissModalClick("data de nascimento") },
                                        content = {
                                            InputField(
                                                label = stringResource(id = R.string.label_data_nascimento),
                                                enabled = false,
                                                value = formatDate(meuPerfilState.dataNascimento),
                                                supportingText = stringResource(id = R.string.input_message_error_data_nascimento),
                                                startIcon = Calendario,
                                                onIconClick = { dateDialogState.show() }
                                            )
                                        },
                                        onSaveClick = {
                                            Toast.makeText(
                                                context,
                                                "Data de Nascimento atualizada com sucesso",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.onDismissModalClick("data de nascimento")
                                        }
                                    )

                                    CustomDatePickerDialog(
                                        dialogState = dateDialogState,
                                        allowedDateValidator = {
                                            it.isBefore(LocalDate.now().minusYears(18))
                                        },
                                        selectedDate = meuPerfilState.dataNascimento,
                                        onDateChange = { novaData ->
                                            viewModel.onChangeEvent(
                                                MeuPerfilField.DataNascimento(
                                                    novaData
                                                )
                                            )
                                        }
                                    )
                                }

                                meuPerfilState.isFotoDialogEnabled -> {
                                    CustomDialog(
                                        saveButtonColor = Amarelo,
                                        saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                        onDismissRequest = { viewModel.onDismissModalClick("foto") },
                                        content = {
                                            Column(
                                                modifier = Modifier,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                if (meuPerfilState.novaFoto == null) {
                                                    Image(
                                                        painter = painterResource(id = R.mipmap.usuario_selecionar_foto),
                                                        contentDescription = null,
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier
                                                            .size(220.dp)
                                                            .border(2.dp, Azul, CircleShape)
                                                            .clip(CircleShape)
                                                            .background(CinzaF5)
                                                            .scale(0.6f)
                                                    )
                                                } else {
                                                    AsyncImage(
                                                        model = meuPerfilState.fotoAtual,
                                                        contentDescription = null,
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier
                                                            .size(220.dp)
                                                            .border(2.dp, Azul, CircleShape)
                                                            .clip(CircleShape)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(12.dp))
                                                TextButton(onClick = {
                                                    singlePhotoPickerLauncher.launch(
                                                        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                    )
                                                }) {
                                                    Text(
                                                        text = stringResource(id = R.string.alterar_foto),
                                                        color = Azul,
                                                        style = MaterialTheme.typography.labelLarge
                                                    )
                                                }
                                            }
                                        },
                                        onSaveClick = {
                                            Toast.makeText(
                                                context,
                                                "Foto de perfil atualizada com sucesso",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.onDismissModalClick("foto")
                                        }
                                    )
                                }

                                meuPerfilState.isEnderecoDialogEnabled -> {
                                    CustomDialog(
                                        saveButtonColor = Amarelo,
                                        saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                        onDismissRequest = { viewModel.onDismissModalClick("endereço") },
                                        content = {
                                            Column(
                                                modifier = Modifier,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                InputField(
                                                    label = stringResource(id = R.string.label_cep),
                                                    value = meuPerfilState.enderecoCep,
                                                    handleChange = {
                                                        if (it.length < 9) {
                                                            viewModel.onChangeEvent(
                                                                MeuPerfilField.EnderecoCep(
                                                                    it
                                                                )
                                                            )
                                                        }
                                                    },
                                                    isError = validations.isCepInvalido,
                                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                    supportingText = stringResource(id = R.string.input_message_error_cep),
                                                    visualTransformation = CepVisualTransformation(),
                                                    onIconClick = { viewModel.handleSearchCep() },
                                                    endIcon = Procurar
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                InputField(
                                                    label = stringResource(id = R.string.label_cidade_uf),
                                                    value = if (meuPerfilState.enderecoUf == "") ""
                                                    else
                                                        stringResource(
                                                            id = R.string.viagem_cidade_uf,
                                                            meuPerfilState.enderecoCidade,
                                                            meuPerfilState.enderecoUf
                                                        ),
                                                    enabled = false,
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                InputField(
                                                    label = stringResource(id = R.string.label_bairro),
                                                    value = meuPerfilState.enderecoBairro,
                                                    enabled = false,
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                InputField(
                                                    label = stringResource(id = R.string.label_logradouro),
                                                    value = meuPerfilState.enderecoLogradouro,
                                                    enabled = false,
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                InputField(
                                                    label = stringResource(id = R.string.numero),
                                                    value = if (meuPerfilState.enderecoNumero == 0) "" else meuPerfilState.enderecoNumero.toString(),
                                                    handleChange = {
                                                        viewModel.onChangeEvent(
                                                            MeuPerfilField.EnderecoNumero(it.toInt())
                                                        )
                                                    },
                                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                                    isError = validations.isNumeroInvalido,
                                                    supportingText = stringResource(id = R.string.input_message_error_numero)
                                                )
                                            }
                                        },
                                        onSaveClick = {
                                            Toast.makeText(
                                                context,
                                                "Endereço atualizado com sucesso",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            viewModel.onDismissModalClick("endereço")
                                        }
                                    )
                                }

                                meuPerfilState.isLogoutDialogEnabled -> {
                                    CustomDialog(
                                        onDismissRequest = { viewModel.onDismissModalClick("logout") },
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.message_confirmation_logout),
                                                color = Azul,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        },
                                        saveButtonLabel = stringResource(id = R.string.label_button_sair),
                                        saveButtonColor = VermelhoExcluir
                                    ) {
                                        logout()
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MeuPerfilTopic(
    icon: ImageVector,
    label: String,
    navigate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navigate() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Azul,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Azul,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth(0.92f)
        )
        Icon(
            imageVector = SetaDireita,
            contentDescription = "Navegar",
            tint = Cinza90,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ItemDadosPessoais(
    label: String,
    valor: String,
    editavel: Boolean,
    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(if (editavel) 0.92f else 1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                color = Azul,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = valor,
                color = Azul,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (editavel) {
            IconButton(onClick = { onEditClick() }) {
                Icon(
                    imageVector = Editar,
                    contentDescription = "Editar",
                    tint = Cinza90,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MeuPerfilScreenPreview() {
    CaronaAppTheme {
        MeuPerfilScreen(rememberNavController())
    }
}