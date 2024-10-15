package com.example.caronaapp.features.cadastro

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.service.retrofit.RetrofitService
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Procurar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CadastroEndereco(
    enderecoData: EnderecoCriacaoDto?,
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
        numeroInvalido = it.toInt() <= 0
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
                onIconClick = {
                    GlobalScope.launch {
                        val apiviaCep = RetrofitService.getApiCarona()

                        try {
                            val getEndereco = apiviaCep.getEndereco(cep)

                            if (getEndereco.isSuccessful) {
                                Log.i("viacep", "Resposta: ${getEndereco.body()}")
                                if (getEndereco.body()?.uf != null) {
                                    uf = getEndereco.body()?.uf.toString()
                                    cidade = getEndereco.body()?.cidade.toString()
                                    bairro = getEndereco.body()?.bairro.toString()
                                    logradouro = getEndereco.body()?.logradouro.toString()
                                    cidadeUf = "${getEndereco.body()?.cidade.toString()}, ${getEndereco.body()?.uf.toString()}"
                                } else {
                                    cepInvalido = true
                                    uf = ""
                                    cidade = ""
                                    bairro = ""
                                    logradouro = ""
                                    cidadeUf = ""
                                }
                            } else {
                                Log.e(
                                    "viacep",
                                    "Erro na resposta: ${getEndereco.errorBody()!!.string()}"
                                )
                                cepInvalido = true
                            }

                        } catch (e: Exception) {
                            Log.e("viacep", "Erro ao buscar CEP $cep: ${e.message}")
//                            cepInvalido = true
                        }
                    }
                },
                endIcon = Procurar
            )
            InputField(
                label = stringResource(id = R.string.label_cidade_uf),
                value = cidadeUf,
                handleChange = { cidadeUf = it },
                enabled = false,
            )
            InputField(
                label = stringResource(id = R.string.label_bairro),
                value = bairro,
                handleChange = { bairro = it },
                enabled = false,
            )
            InputField(
                label = stringResource(id = R.string.label_logradouro),
                value = logradouro,
                handleChange = { logradouro = it },
                enabled = false,
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
