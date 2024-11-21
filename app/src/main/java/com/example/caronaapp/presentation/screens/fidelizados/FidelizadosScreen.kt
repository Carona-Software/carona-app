package com.example.caronaapp.presentation.screens.fidelizados

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.presentation.view_models.FidelizadosViewModel
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.EstrelaPreenchida
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.VermelhoExcluir
import com.example.caronaapp.ui.theme.Viagem
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.CustomAsyncImage
import com.example.caronaapp.utils.layout.CustomDefaultImage
import com.example.caronaapp.utils.layout.CustomDialog
import com.example.caronaapp.utils.layout.CustomItemCard
import com.example.caronaapp.utils.layout.LoadingScreen
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun FidelizadosScreen(
    navController: NavController,
    viewModel: FidelizadosViewModel = koinViewModel()
) {
    val fidelizados by viewModel.fidelizados.collectAsState()
    val solicitacoes by viewModel.solicitacoes.collectAsState()
    val isLoadingScreen by viewModel.isLoadingScreen.collectAsState()
    val fidelizadoToDelete by viewModel.fidelizadoToDelete.collectAsState()
    val isRemoveFidelizadoDialogOpened by viewModel.isRemoveFidelizadoDialogOpened.collectAsState()

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

                if (isLoadingScreen) {
                    LoadingScreen(backGround = CinzaF5)
                } else {
                    if (fidelizados != null) {
                        LazyColumn {
                            items(items = fidelizados!!.toList()) { fidelizado ->
                                FidelizadoCard(
                                    fotoUrl = fidelizado.fotoUrl,
                                    isUrlFotoValida = fidelizado.isFotoValida,
                                    fidelizadoData = fidelizado,
                                    onNegativeButton = { viewModel.onRemoverClick(fidelizado) },
                                    onPositiveButton = { navController.navigate("chat") }
                                )
                            }
                        }
                    } else {
                        NoResultsComponent(
                            text = stringResource(id = R.string.sem_conteudo_fidelizados),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }

                    if (solicitacoes != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .padding(top = 12.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.solicitacoes_pendentes),
                                color = Azul,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)

                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn {
                                items(items = solicitacoes!!.toList()) { solicitacao ->
                                    FidelizadoCard(
                                        fotoUrl = solicitacao.passageiro.fotoUrl,
                                        isUrlFotoValida = solicitacao.passageiro.isFotoValida,
                                        fidelizadoData = solicitacao.passageiro,
                                        isSolicitacao = true,
                                        onNegativeButton = {
                                            viewModel.handleRefuseFidelizado(solicitacao)
                                        },
                                        onPositiveButton = {
                                            viewModel.handleAcceptFidelizado(solicitacao)
                                        }
                                    )
                                }
                            }
                        }
                    }

                    if (isRemoveFidelizadoDialogOpened) {
                        CustomDialog(
                            onDismissRequest = { viewModel.onDismissDialog() },
                            content = {
                                val message = stringResource(
                                    id = R.string.message_confirmation_remove_fidelizado,
                                    fidelizadoToDelete!!.nome,
                                )

                                // Estilizar o texto para deixar em negrito apenas uma parte da string
                                val annotatedString = buildAnnotatedString {
                                    val nomeIndex = message.indexOf(fidelizadoToDelete!!.nome)

                                    append(message.substring(0, nomeIndex))

                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(fidelizadoToDelete!!.nome)
                                    }

                                    append(
                                        message.substring(
                                            nomeIndex + fidelizadoToDelete!!.nome.length
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
                            viewModel.handleDeleteFidelizado()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FidelizadoCard(
    fotoUrl: String,
    isUrlFotoValida: Boolean,
    fidelizadoData: FidelizadoListagemDto,
    isSolicitacao: Boolean = false,
    onNegativeButton: () -> Unit,
    onPositiveButton: () -> Unit,
) {
    CustomItemCard {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            if (isUrlFotoValida) {
                CustomAsyncImage(
                    fotoUrl = fotoUrl,
                    modifier = Modifier
                        .width(68.dp)
                )
            } else {
                CustomDefaultImage(modifier = Modifier.width(68.dp))
            }
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = fidelizadoData.nome,
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
                            imageVector = EstrelaPreenchida,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Amarelo
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (fidelizadoData.notaGeral > 0.0) fidelizadoData.notaGeral.toString() else "--",
                            color = Cinza90,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Localizacao,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Azul
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(
                                id = R.string.fidelizado_localidade,
                                fidelizadoData.cidadeLocalidade,
                                fidelizadoData.ufLocalidade
                            ),
                            color = Azul,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Viagem,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Azul
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(
                                id = R.string.fidelizado_qtd_viagens_juntos,
                                fidelizadoData.qtdViagensJuntos,
                            ),
                            color = Azul,
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
                    label =
                    if (isSolicitacao) stringResource(id = R.string.label_button_recusar)
                    else stringResource(
                        id = R.string.label_button_remover
                    ),
                    backGround = VermelhoExcluir
                ) { onNegativeButton() }
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(
                    label =
                    if (isSolicitacao) stringResource(id = R.string.label_button_aceitar)
                    else stringResource(
                        id = R.string.label_button_conversar
                    ),
                    backGround = Azul
                ) { onPositiveButton() }
            }
        }
    }
}

@Preview
@Composable
fun FidelizadosScreenPreview() {
    FidelizadosScreen(navController = rememberNavController())
}