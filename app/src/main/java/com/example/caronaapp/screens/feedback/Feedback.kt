package com.example.caronaapp.screens.feedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.EstrelaPreenchida
import com.example.caronaapp.ui.theme.EstrelaVazada
import com.example.caronaapp.utils.feedbackFactory
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.TopBarTitle
import com.example.caronaapp.view_models.FeedbackViewModel

@Composable
fun FeedbackScreen(navController: NavController, viagemId: Int?, usuarioId: Int?) {

    val viewModel = viewModel<FeedbackViewModel>(
        factory = feedbackFactory()
    )

    LaunchedEffect(key1 = viagemId, key2 = usuarioId) {
        viewModel.setupFeedback(viagemId!!, usuarioId!!)
    }

    val usuarioAvaliado by viewModel.usuarioAvaliado.collectAsState()
    val criteriosFeedback by viewModel.criteriosFeedback.collectAsState()

    val feedbackState by viewModel.feedbackState.collectAsState()

    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(color = CinzaE8, thickness = 2.dp)
                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    ButtonAction(
                        label = stringResource(id = R.string.label_button_avaliar),
                        handleClick = { viewModel.saveFeedback() }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TopBarTitle(navController = navController, title = "Feedback")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.foto_gustavo),
                            contentDescription = usuarioAvaliado?.nome,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = usuarioAvaliado?.nome ?: "",
                                style = MaterialTheme.typography.labelLarge,
                                color = Azul
                            )
                            Text(
                                text = "Motorista",
                                style = MaterialTheme.typography.displayLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }

                HorizontalDivider(color = CinzaE8, thickness = 2.dp)

                Column(
                    modifier = Modifier
                        .background(CinzaF5)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    criteriosFeedback?.map { criterio ->
                        FeedbackSection(label = criterio.nome,
                            value = viewModel.getNotaCriterio(criterio.id),
                            onStarClick = { nota ->
                                viewModel.onChangeEvent(FeedbackField.Criterio(criterio.id, nota))
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.comentario),
                            style = MaterialTheme.typography.labelLarge,
                            color = Azul
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        BasicTextField(
                            value = feedbackState.comentario,
                            onValueChange = {
                                if (feedbackState.comentario.length <= 256) {
                                    viewModel.onChangeEvent(FeedbackField.Comentario(it))
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .border(
                                    width = 2.dp,
                                    color = Azul,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .padding(8.dp),
                            textStyle = TextStyle(fontSize = 16.sp, color = Azul),

                            )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun FeedbackSection(
    label: String,
    value: Double,
    onStarClick: (Double) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Azul
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            for (i in 1..5) {
                IconButton(
                    onClick = { onStarClick(i * 1.0) },
                    modifier = Modifier
                        .size(52.dp)
                ) {
                    Icon(
                        imageVector = if (i <= (value / 1)) EstrelaPreenchida
                        else EstrelaVazada,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        tint = Azul
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewFeedbackScreen() {
    CaronaAppTheme {
        FeedbackScreen(rememberNavController(), 1, 1)
    }
}