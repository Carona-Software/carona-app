package com.example.caronaapp.screens.cadastro

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.utils.isCpfValido
import com.example.caronaapp.utils.isEmailValid
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CadastroPessoais(
    userData: UsuarioCriacaoDto,
    onClick: (
        nome: String,
        email: String,
        cpf: String,
        genero: String,
        dataNascimento: String
    ) -> Unit
) {
    val context = LocalContext.current

    var nome by remember { mutableStateOf(userData.nome) }
    var email by remember { mutableStateOf(userData.email) }
    var cpf by remember { mutableStateOf(userData.cpf) }
    var genero by remember { mutableStateOf(userData.genero) }

    var nomeInvalido by remember { mutableStateOf(false) }
    var emailInvalido by remember { mutableStateOf(false) }
    var cpfInvalido by remember { mutableStateOf(false) }
    var dataNascimentoInvalida by remember { mutableStateOf(false) }

    // DateTime Picker
    var dataNascimento by remember { mutableStateOf(LocalDate.now()) }

    val dataFormatada by remember {
        derivedStateOf { DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataNascimento) }
    }

    val dateDialogState = rememberMaterialDialogState()
    // Fim de DateTime Picker

    val generoOptions = listOf(
        stringResource(id = R.string.masculino),
        stringResource(id = R.string.feminino),
        stringResource(id = R.string.outros),
    )

    fun setGenero(option: String) {
        genero = if (option == genero) "" else option
    }

    fun onNomeChange(it: String) {
        nome = it
        nomeInvalido = isNomeValido(nome)
    }

    fun onEmailChange(it: String) {
        email = it
        emailInvalido = isEmailValid(email)
    }

    fun onCpfChange(it: String) {
        if (it.length < 12) {
            cpf = it
            cpfInvalido = isCpfValido(cpf)
        }
    }

    fun onButtonClick() {
        if (!nomeInvalido &&
            !emailInvalido &&
            !cpfInvalido &&
            !dataNascimentoInvalida &&
            genero.isNotBlank()
        ) {
            onClick(nome, email, cpf, genero, dataNascimento.toString())
        } else {
            Toast.makeText(context, "Preencha corretamente os campos", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InputField(
            label = stringResource(id = R.string.label_nome),
            value = nome,
            handleChange = { onNomeChange(it) },
            supportingText = stringResource(id = R.string.input_message_error_nome),
            isError = nomeInvalido
        )
        InputField(
            label = stringResource(id = R.string.label_email),
            value = email,
            handleChange = { onEmailChange(it) },
            supportingText = stringResource(id = R.string.input_message_error_email),
            isError = emailInvalido,
        )
        InputField(
            label = stringResource(id = R.string.label_cpf),
            value = cpf,
            handleChange = { onCpfChange(it) },
            supportingText = stringResource(id = R.string.input_message_error_cpf),
            isError = cpfInvalido,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            visualTransformation = CpfVisualTransformation()
        )
        InputField(
            label = stringResource(id = R.string.label_data_nascimento),
            value = dataFormatada.toString(),
            enabled = false,
            handleChange = {},
            supportingText = stringResource(id = R.string.input_message_error_data_nascimento),
            isError = dataNascimentoInvalida,
            startIcon = Calendario,
            onIconClick = {
                dateDialogState.show()
            }
        )

        CustomDatePickerDialog(
            dialogState = dateDialogState,
            allowedDateValidator = {
                it.isBefore(LocalDate.now().minusYears(18))
            }) { novaData ->
            dataNascimento = novaData
        }

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

        ButtonAction(
            label = stringResource(id = R.string.label_button_proximo),
            handleClick = { onButtonClick() }
        )
    }
}

private fun isNomeValido(nome: String): Boolean {
    return nome.isNotBlank() && nome.length < 5
}
