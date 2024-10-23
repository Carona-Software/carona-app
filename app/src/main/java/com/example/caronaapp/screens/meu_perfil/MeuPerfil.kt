package com.example.caronaapp.screens.meu_perfil

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.derivedStateOf
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
import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.dto.endereco.PrincipalTrajeto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.service.RetrofitService
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
import com.example.caronaapp.ui.theme.Estrela
import com.example.caronaapp.ui.theme.Fidelizacao
import com.example.caronaapp.ui.theme.Notificacao
import com.example.caronaapp.ui.theme.Procurar
import com.example.caronaapp.ui.theme.SetaDireita
import com.example.caronaapp.utils.isEmailValid
import com.example.caronaapp.utils.layout.BottomNavBar
import com.example.caronaapp.utils.layout.CriterioFeedback
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.InputField
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MeuPerfilScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val dateDialogState = rememberMaterialDialogState()

    var isNomeDialogEnabled by remember { mutableStateOf(false) }
    var isEmailDialogEnabled by remember { mutableStateOf(false) }
    var isDataNascimentoDialogEnabled by remember { mutableStateOf(false) }
    var isEnderecoDialogEnabled by remember { mutableStateOf(false) }
    var isFotoDialogEnabled by remember { mutableStateOf(false) }

    var novoNome by remember { mutableStateOf("Gustavo Medeiros") }
    var novoEmail by remember { mutableStateOf("gustavo@guga.com") }
    var novaDataNascimento by remember { mutableStateOf(LocalDate.now()) }
    val dataFormatada by remember {
        derivedStateOf { DateTimeFormatter.ofPattern("dd/MM/yyyy").format(novaDataNascimento) }
    }
    var novaFoto by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        novaFoto = uri
    }

    var nomeInvalido by remember { mutableStateOf(false) }
    var emailInvalido by remember { mutableStateOf(false) }
    var fotoInvalida by remember { mutableStateOf(false) }

    // Endereço
    var cep by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }
    var cidadeUf by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf(0) }
    var logradouro by remember { mutableStateOf("") }

    var cepInvalido by remember { mutableStateOf(false) }
    var numeroInvalido by remember { mutableStateOf(false) }

    val userData = UsuarioDetalhesListagemDto(
        id = 1,
        nome = "Gustavo Medeiros",
        email = "gustavo@email.com",
        cpf = "50378814800",
        genero = "MASCULINO",
        perfil = "MOTORISTA",
        dataNascimento = LocalDate.now(),
        fotoUrl = "urlFoto_1dac3571d57d",
        notaMedia = 4.1,
        endereco = EnderecoListagemDto(
            id = 1,
            cep = "04244000",
            uf = "SP",
            cidade = "São Paulo",
            logradouro = "Estrada das Lágrimas",
            bairro = "São João CLímaco",
            numero = 3621
        ),
        avaliacoes = listOf(
            FeedbackListagemDto(
                data = LocalDate.now(),
                comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                avaliador = FeedbackListagemDto.AvaliadorDto(
                    id = 1,
                    nome = "Kaiky Cruz",
                    fotoUrl = "foto_kaiky"
                ),
                notaMedia = 4.3,
                notasCriterios = listOf(
                    NotaCriterioListagemDto(
                        criterio = "Comunicação",
                        nota = 2.0
                    ),
                    NotaCriterioListagemDto(
                        criterio = "Pontualidade",
                        nota = 5.0
                    ),
                    NotaCriterioListagemDto(
                        criterio = "Dirigibilidade",
                        nota = 3.5
                    ),
                    NotaCriterioListagemDto(
                        criterio = "Segurança",
                        nota = 4.0
                    )
                )
            ),
            FeedbackListagemDto(
                data = LocalDate.now(),
                comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                avaliador = FeedbackListagemDto.AvaliadorDto(
                    id = 1,
                    nome = "Kaiky Cruz",
                    fotoUrl = "foto_kaiky"
                ),
                notaMedia = 3.2,
                notasCriterios = listOf(
                    NotaCriterioListagemDto(
                        criterio = "Comunicação",
                        nota = 4.0
                    ),
                    NotaCriterioListagemDto(
                        criterio = "Pontualidade",
                        nota = 2.0
                    ),
                    NotaCriterioListagemDto(
                        criterio = "Dirigibilidade",
                        nota = 5.0
                    ),
                    NotaCriterioListagemDto(
                        criterio = "Segurança",
                        nota = 1.0
                    )
                )
            )
        ),
        carros = listOf(
            UsuarioDetalhesListagemDto.CarroDto(
                marca = "Honda",
                modelo = "Fit",
                cor = "Preto",
                placa = "ABC1D06"
            )
        ),
        fidelizados = listOf(
            FidelizadoListagemDto(
                id = 2,
                nome = "Kaiky Cruz",
                fotoUrl = "foto_kaiky",
                notaGeral = 3.5,
                ufLocalidade = "SP",
                cidadeLocalidade = "São Paulo",
                qtdViagensJuntos = 1
            )
        ),
        viagens = listOf(
            ViagemSimplesListagemDto(
                id = 1,
                data = LocalDate.now(),
                hora = LocalTime.now(),
                preco = 30.0,
                status = StatusViagem.FINALIZADA
            )
        ),
        principalTrajeto = PrincipalTrajeto(
            ufChegada = "SP",
            cidadeChegada = "Campinas",
            ufPartida = "SP",
            cidadePartida = "São Paulo"
        )
    )

    fun onCepChange(it: String) {
        if (it.length < 9) {
            cep = it
//            cepInvalido = isCepValido(it)
        }
    }

    fun onNumeroChange(it: String) {
        numero = it.toInt()
        numeroInvalido = it.toInt() <= 0
    }

    var notaMediaComunicacao by remember { mutableStateOf(0.0) }
    var notaMediaSeguranca by remember { mutableStateOf(0.0) }
    var notaMediaComportamento by remember { mutableStateOf(0.0) }
    var notaMediaPontualidade by remember { mutableStateOf(0.0) }
    var notaMediaDirigibilidade by remember { mutableStateOf(0.0) }

    var percentualComunicacao by remember { mutableStateOf(0f) }
    var percentualSeguranca by remember { mutableStateOf(0f) }
    var percentualComportamento by remember { mutableStateOf(0f) }
    var percentualPontualidade by remember { mutableStateOf(0f) }
    var percentualDirigibilidade by remember { mutableStateOf(0f) }

    fun calculateTotalPorcentageCriterio(notaMedia: Double): Float {
        return ((notaMedia / 5 * 100) / 100).toFloat()
    }

    fun calculateAndRenderCriteriosFeedback() {
        if (userData.avaliacoes.size > 0) {
            GlobalScope.launch {

                var somaTotalComunicacao = 0.0
                var somaTotalSeguranca = 0.0
                var somaTotalComportamento = 0.0
                var somaTotalPontualidade = 0.0
                var somaTotalDirigibilidade = 0.0

                userData.avaliacoes.forEach { avaliacao ->
                    avaliacao.notasCriterios.forEach { criterio ->
                        when (criterio.criterio) {
                            "Comunicação" -> somaTotalComunicacao += criterio.nota
                            "Segurança" -> somaTotalSeguranca += criterio.nota
                            "Comportamento" -> somaTotalComportamento += criterio.nota
                            "Pontualidade" -> somaTotalPontualidade += criterio.nota
                            "Dirigibilidade" -> somaTotalDirigibilidade += criterio.nota;
                            else -> Log.e("feedbacks", "Critério não reconhecido")
                        }
                    }
                }

                val size = userData.avaliacoes.size

                notaMediaComunicacao = somaTotalComunicacao / size
                notaMediaSeguranca = somaTotalSeguranca / size
                notaMediaComportamento = somaTotalComportamento / size
                notaMediaPontualidade = somaTotalPontualidade / size
                notaMediaDirigibilidade = somaTotalDirigibilidade / size

                percentualComunicacao = calculateTotalPorcentageCriterio(somaTotalComunicacao / size)
                percentualSeguranca = calculateTotalPorcentageCriterio(somaTotalSeguranca / size)
                percentualComportamento = calculateTotalPorcentageCriterio(somaTotalComportamento / size)
                percentualPontualidade = calculateTotalPorcentageCriterio(somaTotalPontualidade / size)
                percentualDirigibilidade = calculateTotalPorcentageCriterio(somaTotalDirigibilidade / size)
            }
        }
    }

    calculateAndRenderCriteriosFeedback()

    CaronaAppTheme {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.foto_gustavo),
                        contentDescription = "Minha foto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable { isFotoDialogEnabled = true }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = userData.nome,
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
                                imageVector = Estrela,
                                contentDescription = "Estrela",
                                tint = Amarelo,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.nota_media_usuario, userData.notaMedia),
                                color = Azul,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) { // column de critérios de feedback
                        CriterioFeedback(
                            label = stringResource(id = R.string.dirigibilidade),
                            notaMedia = notaMediaDirigibilidade,
                            percentualNotaMedia = percentualDirigibilidade
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CriterioFeedback(
                            label = stringResource(id = R.string.segurança),
                            notaMedia = notaMediaSeguranca,
                            percentualNotaMedia = percentualSeguranca
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CriterioFeedback(
                            label = stringResource(id = R.string.comunicação),
                            notaMedia = notaMediaComunicacao,
                            percentualNotaMedia = percentualComunicacao
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CriterioFeedback(
                            label = stringResource(id = R.string.pontualidade),
                            notaMedia = notaMediaPontualidade,
                            percentualNotaMedia = percentualPontualidade
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
                        MeuPerfilTopic(
                            icon = Notificacao,
                            label = stringResource(id = R.string.notificacoes)
                        ) {
                            navController.navigate("meu-perfil/notificacoes")
                        }
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
                        Spacer(modifier = Modifier.height(8.dp))
                        MeuPerfilTopic(
                            icon = Carro,
                            label = stringResource(id = R.string.carros)
                        ) {
                            navController.navigate("meu-perfil/carros")
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
                            valor = userData.nome,
                            editavel = true
                        ) {
                            isNomeDialogEnabled = true
                        }
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_email),
                            valor = userData.email,
                            editavel = true,
                        ) {
                            isEmailDialogEnabled = true
                        }
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_data_nascimento),
                            valor = userData.dataNascimento.toString(),
                            editavel = true
                        ) {
                            isDataNascimentoDialogEnabled = true
                        }
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_cpf),
                            valor = userData.cpf,
                            editavel = false
                        )
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.perfil),
                            valor = userData.perfil.toLowerCase().replaceFirstChar { it.uppercase() },
                            editavel = false
                        )
                        ItemDadosPessoais(
                            label = stringResource(id = R.string.label_genero),
                            valor = userData.genero.toLowerCase().replaceFirstChar { it.uppercase() },
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
                            IconButton(onClick = { isEnderecoDialogEnabled = true }) {
                                Icon(
                                    imageVector = Editar,
                                    contentDescription = "Editar",
                                    tint = Cinza90,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    when {
                        isNomeDialogEnabled -> {
                            CustomDialog(
                                saveButtonColor = Amarelo,
                                saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                onDismissRequest = { isNomeDialogEnabled = false },
                                content = {
                                    InputField(
                                        label = stringResource(id = R.string.label_nome),
                                        value = novoNome,
                                        handleChange = { novoNome = it },
                                        supportingText = stringResource(id = R.string.input_message_error_nome),
                                        isError = nomeInvalido,
                                    )
                                },
                                onSaveClick = {
                                    if (novoNome.isNotBlank() && novoNome.length < 5) {
                                        nomeInvalido = true
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Nome atualizado com sucesso",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        isNomeDialogEnabled = false
                                    }
                                }
                            )
                        }

                        isEmailDialogEnabled -> {
                            CustomDialog(
                                saveButtonColor = Amarelo,
                                saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                onDismissRequest = { isEmailDialogEnabled = false },
                                content = {
                                    InputField(
                                        label = stringResource(id = R.string.label_email),
                                        value = novoEmail,
                                        handleChange = { novoEmail = it },
                                        supportingText = stringResource(id = R.string.input_message_error_email),
                                        isError = emailInvalido,
                                    )
                                },
                                onSaveClick = {
                                    if (!isEmailValid(novoEmail)) {
                                        emailInvalido = true
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Email atualizado com sucesso",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        isEmailDialogEnabled = false
                                    }
                                }
                            )
                        }

                        isDataNascimentoDialogEnabled -> {
                            CustomDialog(
                                saveButtonColor = Amarelo,
                                saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                onDismissRequest = { isDataNascimentoDialogEnabled = false },
                                content = {
                                    InputField(
                                        label = stringResource(id = R.string.label_data_nascimento),
                                        enabled = false,
                                        handleChange = {},
                                        value = dataFormatada,
                                        supportingText = stringResource(id = R.string.input_message_error_data_nascimento),
                                        isError = false,
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
                                    isDataNascimentoDialogEnabled = false
                                }
                            )

                            CustomDatePickerDialog(
                                dialogState = dateDialogState,
                                allowedDateValidator = {
                                    it.isBefore(LocalDate.now().minusYears(18))
                                }) { novaData ->
                                novaDataNascimento = novaData
                            }
                        }

                        isFotoDialogEnabled -> {
                            CustomDialog(
                                saveButtonColor = Amarelo,
                                saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                onDismissRequest = { isFotoDialogEnabled = false },
                                content = {
                                    Column(
                                        modifier = Modifier,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        if (novaFoto == null) {
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
                                                model = novaFoto,
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
                                    isFotoDialogEnabled = false
                                }
                            )
                        }

                        isEnderecoDialogEnabled -> {
                            CustomDialog(
                                saveButtonColor = Amarelo,
                                saveButtonLabel = stringResource(id = R.string.label_button_atualizar),
                                onDismissRequest = { isEnderecoDialogEnabled = false },
                                content = {
                                    Column(
                                        modifier = Modifier,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        InputField(
                                            label = stringResource(id = R.string.label_cep),
                                            value = cep,
                                            handleChange = { onCepChange(it) },
                                            isError = cepInvalido,
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            supportingText = stringResource(id = R.string.input_message_error_cep),
//                                      visualTransformation = CepVisualTransformation(),
                                            onIconClick = {
                                                GlobalScope.launch {
                                                    val apiviaCep =
                                                        RetrofitService.getApiViaCepRepository()

                                                    try {
                                                        val getEndereco = apiviaCep.getEndereco(cep)

                                                        if (getEndereco.isSuccessful) {
                                                            Log.i(
                                                                "viacep",
                                                                "Resposta: ${getEndereco.body()}"
                                                            )
                                                            if (getEndereco.body()?.uf != null) {
                                                                uf =
                                                                    getEndereco.body()?.uf.toString()
                                                                cidade =
                                                                    getEndereco.body()?.cidade.toString()
                                                                bairro =
                                                                    getEndereco.body()?.bairro.toString()
                                                                logradouro =
                                                                    getEndereco.body()?.logradouro.toString()
                                                                cidadeUf =
                                                                    "${getEndereco.body()?.cidade.toString()}, ${getEndereco.body()?.uf.toString()}"
                                                            } else {
                                                                cepInvalido = true
                                                                uf = ""
                                                                cidade = ""
                                                                bairro = ""
                                                                logradouro = ""
                                                                cidadeUf = ""
                                                            }
                                                        } else {
                                                            Log.e(
                                                                "viacep",
                                                                "Erro na resposta: ${
                                                                    getEndereco.errorBody()!!
                                                                        .string()
                                                                }"
                                                            )
                                                            cepInvalido = true
                                                        }

                                                    } catch (e: Exception) {
                                                        Log.e(
                                                            "viacep",
                                                            "Erro ao buscar CEP $cep: ${e.message}"
                                                        )
//                            cepInvalido = true
                                                    }
                                                }
                                            },
                                            endIcon = Procurar
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        InputField(
                                            label = stringResource(id = R.string.label_cidade_uf),
                                            value = cidadeUf,
                                            handleChange = { cidadeUf = it },
                                            enabled = false,
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        InputField(
                                            label = stringResource(id = R.string.label_bairro),
                                            value = bairro,
                                            handleChange = { bairro = it },
                                            enabled = false,
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        InputField(
                                            label = stringResource(id = R.string.label_logradouro),
                                            value = logradouro,
                                            handleChange = { logradouro = it },
                                            enabled = false,
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        InputField(
                                            label = stringResource(id = R.string.numero),
                                            value = if (numero == 0) "" else numero.toString(),
                                            handleChange = { onNumeroChange(it) },
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            isError = numeroInvalido,
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
                                    isEnderecoDialogEnabled = false
                                }
                            )
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