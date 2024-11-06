package com.example.caronaapp.presentation.screens.detalhes_viagem

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
import com.example.caronaapp.utils.formatDate
import com.example.caronaapp.utils.formatTime
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CardButton
import com.example.caronaapp.utils.layout.NoResultsComponent
import com.example.caronaapp.utils.layout.TopBarTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetalhesViagemScreen(
    navController: NavController,
    viagemId: Int,
    viewModel: DetalhesViagemViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = viagemId) {
        viewModel.getDetalhesViagem(viagemId)
        viewModel.getSolicitacoesViagem(viagemId)
    }

    // pegar do DataStore (PERFIL, ID)
    val perfilUser = "MOTORISTA"
//    val perfilUser = "PASSAGEIRO"
    val idUser = 1

    val viagem by viewModel.viagem.collectAsState()
    val solicitacoes by viewModel.solicitacoes.collectAsState()

    val isViagemDeleted by viewModel.isViagemDeleted.collectAsState()

    val scrollState = rememberScrollState()

    if (isViagemDeleted) {
        navController.popBackStack()
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

                if (viagem == null) {
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
                                        formatDate(viagem!!.data),
                                        formatTime(viagem!!.horarioSaida)
                                    ),
                                    color = Azul,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            EnderecoComponent(
                                icon = PontoPartida,
                                endereco = viagem!!.trajeto.pontoPartida
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            EnderecoComponent(
                                icon = Localizacao,
                                endereco = viagem!!.trajeto.pontoChegada
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
                                nome = "Gustavo Medeiros",
                                notaMedia = "--",
                                isNavegable = true
                            )
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
                                    painter = painterResource(id = R.drawable.carro_preto),
                                    contentDescription = "Carro",
                                    modifier = Modifier
                                        .width(68.dp)
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Honda Fit",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Azul,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "ABC1D03",
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

                            if (viagem!!.passageiros != null) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 0.dp, max = 200.dp)
                                ) {
                                    items(items = viagem!!.passageiros!!.toList()) { passageiro ->
                                        UserRow(
                                            nome = passageiro.nome,
                                            notaMedia = passageiro.notaGeral.toString(),
                                            isLast = passageiro == viagem!!.passageiros!!.last(),
                                            isClickable = false
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
                            solicitacoes != null &&
                            perfilUser == "MOTORISTA" &&
                            viagem!!.status == StatusViagem.PENDENTE
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
                                    items(items = solicitacoes!!.toList()) { solicitacao ->
                                        SolicitacaoUserRow(
                                            nome = solicitacao.usuario.nome,
                                            notaMedia = solicitacao.usuario.notaGeral.toString(),
                                            isLast = solicitacao == solicitacoes!!.last(),
                                            onRefuseButton = { viewModel.onRefuseClick(solicitacao) },
                                            onAcceptButton = { viewModel.onAcceptClick(solicitacao) }
                                        )
                                    }
                                }
                            }
                        }

                        if (
                            viagem!!.status == StatusViagem.PENDENTE ||
                            (viagem!!.status == StatusViagem.ANDAMENTO && perfilUser == "MOTORISTA")
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
                            when (viagem!!.status) {
                                StatusViagem.PENDENTE -> {
                                    if (perfilUser == "MOTORISTA") {
                                        ButtonAction(label = stringResource(id = R.string.label_button_iniciar)) {}
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    ButtonAction(
                                        label = if (perfilUser == "MOTORISTA") stringResource(id = R.string.label_button_cancelar_viagem)
                                        else stringResource(id = R.string.label_button_cancelar_reserva),
                                        background = Color.White,
                                        labelColor = VermelhoExcluir
                                    ) {
                                        if (perfilUser == "MOTORISTA") {
                                            viewModel.handleCancelViagem(viagemId)
                                        } else {
                                            viewModel.handleCancelReserva(idUser)
                                        }
                                    }
                                }

                                StatusViagem.ANDAMENTO -> {
                                    if (perfilUser == "MOTORISTA") {
                                        ButtonAction(label = stringResource(id = R.string.label_button_finalizar)) {}
                                    }
                                }

                                StatusViagem.FINALIZADA -> {}
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
                    endereco.cep,
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
    nome: String,
    notaMedia: String,
    isNavegable: Boolean = false,
    isClickable: Boolean = true,
    onClick: () -> Unit = {},
    isLast: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { if (isClickable) onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.mipmap.foto_gustavo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

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
                    text = notaMedia,
                    color = Cinza90,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        if (isNavegable) {
            Icon(
                imageVector = SetaDireita,
                contentDescription = "Navegar",
                tint = Cinza90,
                modifier = Modifier.size(20.dp)
            )
        }
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
    notaMedia: String,
    onRefuseButton: () -> Unit,
    onAcceptButton: () -> Unit,
    isLast: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.mipmap.foto_gustavo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

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
                    text = notaMedia,
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

@Preview
@Composable
fun DetalhesViagemmScreenPreview() {
    DetalhesViagemScreen(navController = rememberNavController(), 1)
}