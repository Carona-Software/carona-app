package com.example.caronaapp.features.cadastro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.data.Endereco
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.InputField
import com.example.caronaapp.masks.CepVisualTransformation
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun CadastroEndereco(
    enderecoData: Endereco?,
    onClick: (
        String,
        String,
        String,
        String,
        String,
        Int
    ) -> Unit
) {
    var cep by remember { mutableStateOf(enderecoData?.cep ?: "") }
    var cidade by remember { mutableStateOf(enderecoData?.cidade ?: "") }
    var uf by remember { mutableStateOf(enderecoData?.uf ?: "") }
    var cidadeUf by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf(enderecoData?.bairro ?: "") }
    var numero by remember { mutableStateOf(enderecoData?.numero ?: 0) }
    var logradouro by remember { mutableStateOf(enderecoData?.logradouro ?: "") }

    var cepInvalido by remember { mutableStateOf(false) }
    var numeroInvalido by remember { mutableStateOf(false) }

    fun onCepChange(it: String) {
        if (it.length < 9) {
            cep = it
            cepInvalido = isCepValido(it)
        }
    }

    fun onNumeroChange(it: String) {
        numero = it.toInt()
        numeroInvalido = isNumeroValido(numero)
    }

    CaronaAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            InputField(
                label = stringResource(id = R.string.label_cep),
                value = cep,
                handleChange = { onCepChange(it) },
                isError = cepInvalido,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = stringResource(id = R.string.input_message_error_cep),
//                visualTransformation = CepVisualTransformation(),
                endIcon = Calendario
            )
            InputField(
                label = stringResource(id = R.string.label_cidade_uf),
                value = cidadeUf,
                handleChange = { cidadeUf = it },
            )
            InputField(
                label = stringResource(id = R.string.label_bairro),
                value = bairro,
                handleChange = { bairro = it },
            )
            InputField(
                label = stringResource(id = R.string.label_logradouro),
                value = logradouro,
                handleChange = { logradouro = it },
            )
            InputField(
                label = stringResource(id = R.string.numero),
                value = if (numero == 0) "" else numero.toString(),
                handleChange = { onNumeroChange(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = numeroInvalido,
                supportingText = stringResource(id = R.string.input_message_error_numero)
            )

            ButtonAction(
                label = stringResource(id = R.string.label_button_proximo),
                handleClick = { onClick(cep, uf, cidade, bairro, logradouro, numero) }
            )

        }
    }
}

fun isCepValido(cep: String): Boolean {
    return cep.isNotBlank() && cep.length != 8
}

fun isNumeroValido(numero: Int): Boolean {
    return numero <= 0
}