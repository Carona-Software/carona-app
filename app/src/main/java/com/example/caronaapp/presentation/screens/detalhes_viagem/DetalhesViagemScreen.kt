package com.example.caronaapp.presentation.screens.detalhes_viagem

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.viagem.LocalidadeDto
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.presentation.view_models.DetalhesViagemViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Carro
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.EstrelaPreenchida
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.ui.theme.SetaDireita
import com.example.caronaapp.ui.theme.VerdePerto
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.utils.functions.formatCep
import com.example.caronaapp.utils.functions.formatTime
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.utils.functions.returnCorCarro
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetalhesViagemScreen(
    navController: NavController,
    viagemId: Int,
    viewModel: DetalhesViagemViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val idUser by viewModel.idUser.collectAsState()
    val perfilUser by viewModel.perfilUser.collectAsState()
    val state by viewModel.state.collectAsState()
    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = viagemId, key2 = state.isViagemDeleted) {
        viewModel.getDetalhesViagem(viagemId)

        if (state.isViagemDeleted) {
            Toast.makeText(context, state.successMessage, Toast.LENGTH_SHORT).show()
            viewModel.setIsViagemDeletedToFalse()
            navController.popBackStack()
        }

        if (state.isSuccess) {
            Toast.makeText(context, state.successMessage, Toast.LENGTH_SHORT).show()
            delay(200)
            viewModel.setIsSuccessToFalse()
        }

        if (state.isError) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            delay(200)
            viewModel.setIsErrorToFalse()
        }
    }

    CaronaAppTheme {
        Scaffold { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                TopBarTitle(
                    navController = navController,
                    title = stringResource(id = R.string.viagem)
                )

                if (isLoadingScreen) {
                    LoadingScreen()
                } else {
                    if (state.viagem == null) {
                        NoResultsComponent(text = stringResource(id = R.string.sem_conteudo_detalhes_viagem))
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Calendario,
                                        contentDescription = "Calendário",
                                        tint = Azul,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = stringResource(
                                            id = R.string.viagem_data_hora,
                                            state.viagem!!.dataToShow,
                                            formatTime(state.viagem!!.horarioPartidaInTime)
                                        ),
                                        color = Azul,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                EnderecoComponent(
                                    icon = PontoPartida,
                                    endereco = state.viagem!!.trajeto.pontoPartida
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                EnderecoComponent(
                                    icon = Localizacao,
                                    endereco = state.viagem!!.trajeto.pontoChegada
                                )
                                Spacer(modifier = Modifier.height(28.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(
                                            id = R.string.ver_no_mapa
                                        ),
                                        color = Azul,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Icon(
                                        imageVector = SetaDireita,
                                        contentDescription = "Navegar",
                                        tint = Cinza90,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 16.dp),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.motorista),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Azul,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                UserRow(
                                    nome = state.viagem!!.motorista.nome,
                                    fotoUrl = state.viagem!!.motorista.fotoUrl,
                                    isFotoValida = state.viagem!!.motorista.isFotoValida,
                                    notaMedia = state.viagem!!.motorista.notaGeral,
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.carro),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Azul,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp)),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = returnCorCarro(cor = state.viagem!!.carro.cor),
                                        contentDescription = "Carro",
                                        modifier = Modifier.width(68.dp)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = stringResource(
                                                id = R.string.carro_marca_modelo,
                                                state.viagem!!.carro.marca,
                                                state.viagem!!.carro.modelo
                                            ),
                                            style = MaterialTheme.typography.labelLarge,
                                            color = Azul,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = state.viagem!!.carro.placa,
                                            style = MaterialTheme.typography.displayLarge,
                                            color = Cinza90,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 16.dp),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.passageiros),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Azul,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                if (state.viagem!!.passageiros.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(min = 0.dp, max = 200.dp)
                                    ) {
                                        items(items = state.viagem!!.passageiros.toList()) { passageiro ->
                                            UserRow(
                                                nome = passageiro.nome,
                                                fotoUrl = passageiro.fotoUrl,
                                                isFotoValida = passageiro.isFotoValida,
                                                notaMedia = passageiro.notaGeral,
                                                isLast = passageiro == state.viagem!!.passageiros.last(),
                                                modifier = if (idUser == passageiro.id) Modifier
                                                else Modifier.clickable {
                                                    navController.navigate("usuarios/perfil/${passageiro.id}")
                                                },
                                                onFeedbackClick = {
                                                    navController.navigate("feedback/${state.viagem!!.id}/${passageiro.id}")
                                                },
                                                foiAvaliado = passageiro.foiAvaliado
                                            )
                                        }
                                    }
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.sem_conteudo_passageiros),
                                        style = MaterialTheme.typography.displayLarge,
                                        color = Cinza90,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            if (
                                state.viagem!!.solicitacoes.isNotEmpty() &&
                                perfilUser == "MOTORISTA" &&
                                state.viagem!!.status == StatusViagem.PENDENTE
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(vertical = 16.dp),
                                    color = CinzaE8,
                                    thickness = 8.dp
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.solicitacoes_pendentes),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Azul,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(min = 0.dp, max = 200.dp)
                                    ) {
                                        items(items = state.viagem!!.solicitacoes.toList()) { solicitacao ->
                                            SolicitacaoUserRow(
                                                nome = solicitacao.usuario.nome,
                                                fotoUrl = solicitacao.usuario.fotoUrl,
                                                isFotoValida = solicitacao.usuario.isFotoValida,
                                                notaMedia = solicitacao.usuario.notaGeral,
                                                isLast = solicitacao == state.viagem!!.solicitacoes.last(),
                                                navigate = { navController.navigate("usuarios/perfil/${solicitacao.usuario.id}") },
                                                onRefuseButton = {
                                                    viewModel.onRefuseClick(
                                                        solicitacao
                                                    )
                                                },
                                                onAcceptButton = {
                                                    viewModel.onAcceptClick(
                                                        solicitacao
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            if (
                                state.viagem!!.status == StatusViagem.PENDENTE ||
                                (state.viagem!!.status == StatusViagem.ANDAMENTO && perfilUser == "MOTORISTA")
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(vertical = 16.dp),
                                    color = CinzaE8,
                                    thickness = 8.dp
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                            ) {
                                when (state.viagem!!.status) {
                                    StatusViagem.PENDENTE -> {
                                        if (perfilUser == "MOTORISTA") {
                                            ButtonAction(label = stringResource(id = R.string.label_button_iniciar)) {
                                                viewModel.onIniciarViagemClick()
                                            }
                                            Spacer(modifier = Modifier.height(16.dp))
                                            ButtonAction(
                                                label = stringResource(id = R.string.label_button_cancelar_viagem),
                                                background = Color.White,
                                                labelColor = VermelhoExcluir
                                            ) {
                                                viewModel.onCancelViagemClick()
                                            }
                                        } else {
                                            if (state.viagem!!.passageiros.find { it.id == idUser } != null) { // Usuário está na viagem
                                                ButtonAction(
                                                    label = stringResource(id = R.string.label_button_cancelar_reserva),
                                                    background = Color.White,
                                                    labelColor = VermelhoExcluir
                                                ) {
                                                    viewModel.onCancelReservaClick()
                                                }
                                            } else if (state.viagem!!.solicitacoes.find { it.usuario.id == idUser } != null) { // Usuário enviou solicitação
                                                ButtonAction(
                                                    label = stringResource(id = R.string.label_button_cancelar_solicitacao),
                                                    background = Color.White,
                                                    labelColor = VermelhoExcluir
                                                ) {
                                                    viewModel.onCancelSolicitacaoReservaClick(state.viagem!!.solicitacoes.find { it.usuario.id == idUser }!!)
                                                }
                                            } else {
                                                ButtonAction(label = stringResource(id = R.string.label_button_solicitar_carona)) {
                                                    viewModel.handleSolicitarCarona()
                                                }
                                            }
                                        }
                                    }

                                    StatusViagem.ANDAMENTO -> {
                                        if (perfilUser == "MOTORISTA") {
                                            ButtonAction(label = stringResource(id = R.string.label_button_finalizar)) {
                                                viewModel.onFinalizarViagemClick()
                                            }
                                        }
                                    }

                                    StatusViagem.FINALIZADA -> {
                                        if (perfilUser == "PASSAGEIRO" && !state.motoristaFoiAvaliado) {
                                            ButtonAction(
                                                label = stringResource(id = R.string.label_button_avaliar_motorista)
                                            ) {
                                                navController.navigate(
                                                    "feedback/${state.viagem!!.id}/${state.viagem!!.motorista.id}"
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Modal
                            when {
                                state.isCancelViagemModalOpened -> {
                                    CustomDialog(
                                        onDismissRequest = { viewModel.onDismissCancelViagemRequest() },
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.message_confirmation_cancel_viagem),
                                                color = Azul,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        },
                                        saveButtonLabel = stringResource(id = R.string.label_button_cancelar),
                                        saveButtonColor = VermelhoExcluir
                                    ) {
                                        viewModel.handleCancelViagem()
                                    }
                                }

                                state.isCancelReservaModalOpened -> {
                                    CustomDialog(
                                        onDismissRequest = { viewModel.onDismissCancelReservaRequest() },
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.message_confirmation_cancel_reserva),
                                                color = Azul,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        },
                                        saveButtonLabel = stringResource(id = R.string.label_button_cancelar),
                                        saveButtonColor = VermelhoExcluir
                                    ) {
                                        viewModel.handleCancelReserva()
                                    }
                                }

                                state.isCancelSolicitacaoModalOpened -> {
                                    CustomDialog(
                                        onDismissRequest = { viewModel.onDismissCancelSolicitacaoRequest() },
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.message_confirmation_cancel_solicitacao),
                                                color = Azul,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        },
                                        saveButtonLabel = stringResource(id = R.string.label_button_cancelar),
                                        saveButtonColor = VermelhoExcluir
                                    ) {
                                        viewModel.handleCancelSolicitacao()
                                    }
                                }

                                state.isIniciarViagemModalOpened -> {
                                    CustomDialog(
                                        onDismissRequest = { viewModel.onDismissIniciarViagemRequest() },
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.message_confirmation_iniciar_viagem),
                                                color = Azul,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        },
                                        saveButtonLabel = stringResource(id = R.string.label_button_iniciar),
                                        saveButtonColor = Azul
                                    ) {
                                        viewModel.handleIniciarViagem()
                                    }
                                }

                                state.isFinalizarViagemModalOpened -> {
                                    CustomDialog(
                                        onDismissRequest = { viewModel.onDismissFinalizarViagemRequest() },
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.message_confirmation_finalizar_viagem),
                                                color = Azul,
                                                style = MaterialTheme.typography.titleSmall,
                                            )
                                        },
                                        saveButtonLabel = stringResource(id = R.string.label_button_finalizar),
                                        saveButtonColor = Azul
                                    ) {
                                        viewModel.handleFinalizarViagem()
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
fun EnderecoComponent(
    icon: ImageVector,
    endereco: LocalidadeDto
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Azul,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = stringResource(
                    id = R.string.viagem_cidade_uf,
                    endereco.cidade,
                    endereco.uf
                ),
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = R.string.viagem_endereco,
                    endereco.logradouro,
                    endereco.numero,
                    formatCep(endereco.cep),
                    endereco.cidade,
                    endereco.uf
                ),
                color = Cinza90,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(VerdePerto)
                        .padding(3.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Carro,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        id = R.string.viagem_texto_distancia_ponto_de_partida,
                        0.6
                    ),
                    color = VerdePerto,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun UserRow(
    modifier: Modifier = Modifier,
    nome: String,
    notaMedia: Double,
    fotoUrl: String,
    isFotoValida: Boolean,
    foiAvaliado: Boolean = false,
    viagemFinalizada: Boolean = false,
    onFeedbackClick: () -> Unit = {},
    isLast: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isFotoValida) {
            CustomAsyncImage(
                fotoUrl = fotoUrl,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .align(Alignment.Top)
            )
        } else {
            CustomDefaultImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .align(Alignment.Top)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = nome,
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = EstrelaPreenchida,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Amarelo
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (notaMedia == 0.0) "--" else notaMedia.toString(),
                    color = Cinza90,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            if (!foiAvaliado && viagemFinalizada) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) { // botões
                    CardButton(
                        label = stringResource(id = R.string.label_button_avaliar),
                        backGround = Amarelo
                    ) { onFeedbackClick() }
                }
            }
        }

        Icon(
            imageVector = SetaDireita,
            contentDescription = "Navegar",
            tint = Cinza90,
            modifier = Modifier.size(20.dp)
        )
    }

    if (!isLast) {
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp),
            color = CinzaE8,
            thickness = 1.dp
        )
    }
}

@Composable
fun SolicitacaoUserRow(
    nome: String,
    notaMedia: Double,
    fotoUrl: String,
    isFotoValida: Boolean,
    navigate: () -> Unit,
    onRefuseButton: () -> Unit,
    onAcceptButton: () -> Unit,
    isLast: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { navigate() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isFotoValida) {
            CustomAsyncImage(
                fotoUrl = fotoUrl,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .align(Alignment.Top)
            )
        } else {
            CustomDefaultImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .align(Alignment.Top)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = nome,
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = EstrelaPreenchida,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Amarelo
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (notaMedia == 0.0) "--" else notaMedia.toString(),
                    color = Cinza90,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) { // botões
                CardButton(
                    label = stringResource(id = R.string.label_button_recusar),
                    backGround = VermelhoExcluir
                ) { onRefuseButton() }
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(
                    label = stringResource(id = R.string.label_button_aceitar),
                    backGround = Azul
                ) { onAcceptButton() }
            }
        }

        Icon(
            imageVector = SetaDireita,
            contentDescription = "Navegar",
            tint = Cinza90,
            modifier = Modifier.size(20.dp)
        )

    }

    if (!isLast) {
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 16.dp),
            color = CinzaE8,
            thickness = 1.dp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetalhesViagemmScreenPreview() {
    CaronaAppTheme {
        UserRow(
            nome = "Gustavo Medeiros",
            fotoUrl = "",
            isFotoValida = false,
            notaMedia = 4.5,
            viagemFinalizada = false,
        )

    }
}