package com.example.caronaapp.screens.cadastro

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Procurar
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.utils.masks.CepVisualTransformation

@Composable
fun CadastroEndereco(
    userData: UserCadastroState,
    onChangeEvent: (CadastroField) -> Unit,
    handleSearchCep: () -> Unit,
    onNextClick: () -> Unit,
    validations: UserCadastroValidations
) {
    val context = LocalContext.current

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
                value = userData.enderecoCep,
                handleChange = {
                    if (it.length < 9) {
                        onChangeEvent(CadastroField.EnderecoCep(it))
                    }
                },
                isError = validations.isCepInvalido,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = stringResource(id = R.string.input_message_error_cep),
                visualTransformation = CepVisualTransformation(),
                onIconClick = { handleSearchCep() },
                endIcon = Procurar
            )
            InputField(
                label = stringResource(id = R.string.label_cidade_uf),
                value = if (userData.enderecoCidade == "") "" else stringResource(
                    id = R.string.viagem_cidade_uf,
                    userData.enderecoCidade,
                    userData.enderecoUf
                ),
                enabled = false,
            )
            InputField(
                label = stringResource(id = R.string.label_bairro),
                value = userData.enderecoBairro,
                enabled = false,
            )
            InputField(
                label = stringResource(id = R.string.label_logradouro),
                value = userData.enderecoLogradouro,
                enabled = false,
            )
            InputField(
                label = stringResource(id = R.string.numero),
                value = if (userData.enderecoNumero == 0) "" else userData.enderecoNumero.toString(),
                handleChange = { onChangeEvent(CadastroField.EnderecoNumero(it.toInt())) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = validations.isNumeroInvalido,
                supportingText = stringResource(id = R.string.input_message_error_numero)
            )

            ButtonAction(
                label = stringResource(id = R.string.label_button_proximo),
                handleClick = {
                    if (!validations.isCepInvalido &&
                        !validations.isNumeroInvalido &&
                        userData.enderecoUf != "" &&
                        userData.enderecoCidade != "" &&
                        userData.enderecoBairro != "" &&
                        userData.enderecoLogradouro != ""
                    ) {
                        onNextClick()
                    } else {
                        Toast.makeText(
                            context,
                            "Preencha os campos de endereço",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

        }
    }
}


