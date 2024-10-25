package com.example.caronaapp.features.feedback

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaE8
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.TopBarTitle

@Composable
fun FeedbackScreen(navController: NavController) {
    var comentario by remember { mutableStateOf("") }
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
                        handleClick = { }
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
                            contentDescription = "Foto do motorista",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Gustavo",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Azul
                            )
                            Text(
                                text = "Motorista",
                                style = MaterialTheme.typography.bodySmall,
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

                    FeedbackSection(label = stringResource(id = R.string.dirigibilidade))
                    FeedbackSection(label = stringResource(id = R.string.seguranca))
                    FeedbackSection(label = stringResource(id = R.string.comunicacao))
                    FeedbackSection(label = stringResource(id = R.string.pontualidade))

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
                            value = comentario,
                            onValueChange = { comentario = it },
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
fun FeedbackSection(label: String) {
    var selectedRating by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Azul)
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            for (i in 1..5) {
                IconButton(
                    onClick = { selectedRating = i },
                    modifier = Modifier
                        .size(52.dp)
                ) {
                    Icon(
                        imageVector = if (i <= selectedRating) Icons.Default.Star else Icons.Default.StarBorder,
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
        FeedbackScreen(rememberNavController())
    }
}
