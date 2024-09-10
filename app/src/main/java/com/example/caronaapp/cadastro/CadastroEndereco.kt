package com.example.caronaapp.cadastro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.InputField

@Composable
fun CadastroEndereco(onClick: () -> Unit) {
    val contexto = LocalContext.current
    var cep by remember { mutableStateOf("") }
    var cidadeUf by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InputField(
            label = stringResource(id = R.string.label_cep),
            maxLines = 1,
            value = cep, handleChange = { cep = it })
        InputField(
            label = stringResource(id = R.string.label_cidade_uf),
            maxLines = 1,
            value = cidadeUf, handleChange = {})
        InputField(
            label = stringResource(id = R.string.label_bairro),
            maxLines = 1,
            value = bairro, handleChange = {})
        InputField(
            label = stringResource(id = R.string.label_logradouro),
            maxLines = 1,
            value = logradouro, handleChange = {})
        InputField(
            label = stringResource(id = R.string.numero),
            maxLines = 1,
            value = numero, handleChange = {})

        ButtonAction(
            label = stringResource(id = R.string.label_button_proximo),
            handleClick = { onClick() }
        )

    }
}