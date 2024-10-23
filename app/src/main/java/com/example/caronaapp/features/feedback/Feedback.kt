package com.example.caronaapp.features.feedback

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.caronaapp.ui.theme.Amarelo
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

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState),
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
                            .padding(16.dp),
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
            }
            HorizontalDivider(color = CinzaE8, thickness = 2.dp)

            Column(modifier = Modifier.fillMaxSize().background(CinzaF5)) {
                FeedbackSection(label = "Dirigibilidade")
                FeedbackSection(label = "Segurança")
                FeedbackSection(label = "Comunicação")
                FeedbackSection(label = "Pontualidade")

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Comentário",
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
                            .background(Color.White)
                            .border(
                                width = 1.dp,
                                color = Azul,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp),
                        textStyle = TextStyle(fontSize = 16.sp, color = Azul),
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                HorizontalDivider(color = CinzaE8, thickness = 2.dp)
            }
            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                ButtonAction(
                    label = stringResource(id = R.string.label_button_avaliar),
                    handleClick = { }
                )
            }
        }
    }
}

@Composable
fun FeedbackSection(label: String) {
    var selectedRating by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Azul)
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= selectedRating) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(3.dp)
                        .clickable {
                            selectedRating = i
                        },
                    tint = Azul
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewFeedbackScreen() {
    CaronaAppTheme {
        val navController = rememberNavController()
        FeedbackScreen(navController)
    }
}
