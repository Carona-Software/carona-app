package com.example.caronaapp.presentation.screens.perfil_outro_usuario

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.presentation.screens.feature.chat.ChatViewModel
import com.example.caronaapp.presentation.view_models.PerfilOutroUsuarioViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.EstrelaPreenchida
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.Viagem
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CriterioFeedback
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun PerfilOutroUsuarioScreen(
    navController: NavController,
    id: Int,
    viewModel: PerfilOutroUsuarioViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val searchedUser by viewModel.searchedUser.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getDetalhesUsuario(id)

        if (state.isSuccessful || state.isError) {
            Toast.makeText(context, state.messageToDisplay, Toast.LENGTH_SHORT).show()
            delay(200)
            viewModel.setControlVariablesToFalse()
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
                if (state.isLoadingScreen) {
                    LoadingScreen()
                } else {
                    if (state.userData == null) {
                        NoResultsComponent(text = stringResource(id = R.string.sem_conteudo_perfil))
                    } else {
                        TopBarUser(
                            navController = navController,
                            fotoUrl = state.userData!!.fotoUrl,
                            isUrlFotoValida = state.isFotoValida,
                            nome = state.userData!!.nome
                        )
                        Column(
                            // column com scroll
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
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
                                        text = if (state.userData!!.notaMedia == 0.0) "--" else state.userData!!.notaMedia.toString(),
                                        color = Azul,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) { // column de critérios de feedback
                                if (state.userData!!.perfil.uppercase() == "MOTORISTA") {
                                    CriterioFeedback(
                                        label = stringResource(id = R.string.dirigibilidade),
                                        notaMedia = state.avaliacoesCriterioUser.dirigibilidade.notaMedia,
                                        percentualNotaMedia = state.avaliacoesCriterioUser.dirigibilidade.percentual
                                    )
                                } else {
                                    CriterioFeedback(
                                        label = stringResource(id = R.string.comportamento),
                                        notaMedia = state.avaliacoesCriterioUser.comportamento.notaMedia,
                                        percentualNotaMedia = state.avaliacoesCriterioUser.comportamento.percentual
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                CriterioFeedback(
                                    label = stringResource(id = R.string.seguranca),
                                    notaMedia = state.avaliacoesCriterioUser.seguranca.notaMedia,
                                    percentualNotaMedia = state.avaliacoesCriterioUser.seguranca.percentual
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                CriterioFeedback(
                                    label = stringResource(id = R.string.comunicacao),
                                    notaMedia = state.avaliacoesCriterioUser.comunicacao.notaMedia,
                                    percentualNotaMedia = state.avaliacoesCriterioUser.comunicacao.percentual
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                CriterioFeedback(
                                    label = stringResource(id = R.string.pontualidade),
                                    notaMedia = state.avaliacoesCriterioUser.pontualidade.notaMedia,
                                    percentualNotaMedia = state.avaliacoesCriterioUser.pontualidade.percentual
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .scale(1.2f),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Viagem,
                                    contentDescription = "Viagens",
                                    tint = Azul,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = stringResource(
                                        id = R.string.total_viagens_realizadas,
                                        state.userData!!.viagensRealizadas
                                    ),
                                    color = Azul,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.fillMaxWidth(0.92f)
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(vertical = 24.dp)
                                    .scale(1.2f),
                                color = CinzaE8,
                                thickness = 8.dp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Localizacao,
                                    contentDescription = "Localização",
                                    tint = Azul,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = stringResource(
                                        id = R.string.viagem_cidade_uf,
                                        state.userData!!.endereco.cidade,
                                        state.userData!!.endereco.uf
                                    ),
                                    color = Azul,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.fillMaxWidth(0.92f)
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
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.avaliacoes),
                                    color = Azul,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                if (state.userData!!.avaliacoes.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(min = 0.dp, max = 300.dp)
                                    ) {
                                        items(items = state.userData!!.avaliacoes) { avaliacao ->
                                            AvaliacaoItem(
                                                isFotoValida = avaliacao.avaliador.isFotoValida,
                                                fotoUrl = avaliacao.avaliador.fotoUrl,
                                                nome = avaliacao.avaliador.nome,
                                                data = avaliacao.dataInDate,
                                                comentario = avaliacao.comentario,
                                                isLast = state.userData!!.avaliacoes.last() == avaliacao
                                            )
                                        }
                                    }
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.sem_conteudo_avaliacoes),
                                        style = MaterialTheme.typography.displayLarge,
                                        color = Cinza90,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            if (state.userData!!.perfil != state.perfilUser) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(vertical = 24.dp)
                                        .scale(1.2f),
                                    color = CinzaE8,
                                    thickness = 8.dp
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp)
                                ) {
                                    if (state.totalViagensJuntos > 2 && !state.isPassageiroFidelizado) {
                                        ButtonAction(
                                            label = stringResource(id = R.string.label_button_solicitar_fidelizacao),
                                            background = Azul
                                        ) {
                                            viewModel.handleSolicitarFidelizacao()
                                        }
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    val coroutineScope = rememberCoroutineScope()
                                    val chatViewModel = koinViewModel<ChatViewModel>()

                                    ButtonAction(label = stringResource(id = R.string.label_button_conversar)) {
                                        coroutineScope.launch {
                                            chatViewModel.createOrGetChat(
                                                currentUserId = state.currentFirebaseUser,
                                                targetUserId = searchedUser?.userId ?: ""
                                            ) { chatId ->
                                                Log.d(
                                                    "createOrGetChat",
                                                    "IDs recebidos: currentUserId=${state.currentFirebaseUser}, targetUserId=${searchedUser.userId}"
                                                )
                                                Log.d("createOrGetChat", "chatId gerado: $chatId")
                                                navController.navigate(
                                                    "chat/$chatId/${
                                                        Uri.encode(
                                                            searchedUser?.nome ?: ""
                                                        )
                                                    }/${Uri.encode(searchedUser?.fotoUrl ?: "")}"
                                                )
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
    }
}

@Composable
fun AvaliacaoItem(
    isFotoValida: Boolean,
    fotoUrl: String,
    nome: String,
    data: LocalDate,
    comentario: String,
    isLast: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isFotoValida) {
                CustomAsyncImage(fotoUrl = fotoUrl, modifier = Modifier.size(40.dp))
            } else {
                CustomDefaultImage(modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                // nome e data
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = nome,
                    color = Azul,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatDate(data),
                    color = Cinza90,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = comentario,
            color = Azul,
            style = MaterialTheme.typography.labelMedium,
        )
    }

    if (!isLast) {
        HorizontalDivider(
//           modifier = Modifier
//           .padding(vertical = 4.dp),
            color = CinzaE8,
            thickness = 1.dp
        )
    }
}

@Preview
@Composable
fun PerfilOutroUsuarioScreenPreview() {
    PerfilOutroUsuarioScreen(navController = rememberNavController(), 1)
}