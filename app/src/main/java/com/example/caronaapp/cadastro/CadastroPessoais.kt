package com.example.caronaapp.cadastro

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.InputField
import com.example.caronaapp.ui.theme.Azul

@Composable
fun CadastroPessoais(onClick: () -> Unit) {
    val contexto = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }

    val generoOptions = listOf(
        stringResource(id = R.string.masculino),
        stringResource(id = R.string.feminino),
        stringResource(id = R.string.outros),
    )

    fun setGenero(option: String) {
        if (option == genero) genero = "" else genero = option
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InputField(label = stringResource(
            id = R.string.label_nome
        ),
            maxLines = 1,
            value = nome, handleChange = { nome = it }
        )
        InputField(
            label = stringResource(id = R.string.label_email),
            maxLines = 1,
            value = email, handleChange = { email = it }
        )
        InputField(
            label = stringResource(id = R.string.label_cpf),
            maxLines = 1,
            value = cpf, handleChange = { cpf = it }
        )
        InputField(
            label = stringResource(id = R.string.label_data_nascimento),
            maxLines = 1,
            value = dataNascimento, handleChange = { dataNascimento = it }
        )

        Column {
            Text(
                text = stringResource(id = R.string.label_genero),
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        BorderStroke(2.dp, Azul),
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
            ) {
                generoOptions.map {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { setGenero(it) }
                            .background(
                                if (genero == it) Azul
                                else Color.White
                            )
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .padding(4.dp),
                            color = (
                                    if (genero == it) Color.White
                                    else Azul
                                    )
                        )
                    }
                }
            }
        }

        ButtonAction(handleClick = { onClick() })
    }
}

//@Composable
//fun GeneroSet(options: List<String>, generoAtual: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(52.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .border(
//                BorderStroke(2.dp, Azul),
//                shape = RoundedCornerShape(12.dp)
//            ),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        options.map {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.displayLarge,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .fillMaxWidth(0.33f)
//                    .padding(4.dp)
//                    .clickable { }
//                    .background(
//                        if (generoAtual == it) Azul
//                        else Color.White
//                    ),
//                color = (
//                        if (generoAtual == it) Color.White
//                        else Azul
//                        )
//                )
//        }
//    }
//}