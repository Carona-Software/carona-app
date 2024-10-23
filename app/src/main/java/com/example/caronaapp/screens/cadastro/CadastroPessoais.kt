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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.example.caronaapp.utils.layout.InputField
import com.example.caronaapp.utils.masks.CpfVisualTransformation
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CadastroPessoais(
    userData: UserCadastroState,
    onNextClick: () -> Unit,
    onChangeEvent: (CadastroField) -> Unit,
    validations: UserCadastroValidations
) {
    val context = LocalContext.current

    val dateDialogState = rememberMaterialDialogState()

    val generoOptions = listOf(
        stringResource(id = R.string.masculino),
        stringResource(id = R.string.feminino),
        stringResource(id = R.string.outros),
    )

    fun setGenero(option: String) {
        onChangeEvent(CadastroField.Genero(if (option == userData.genero) "" else option))
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
            value = userData.nome,
            handleChange = { onChangeEvent(CadastroField.Nome(it)) },
            supportingText = stringResource(id = R.string.input_message_error_nome),
            isError = validations.isNomeInvalido,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            )
        )
        InputField(
            label = stringResource(id = R.string.label_email),
            value = userData.email,
            handleChange = { onChangeEvent(CadastroField.Email(it)) },
            supportingText = stringResource(id = R.string.input_message_error_email),
            isError = validations.isEmailInvalido,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            )
        )
        InputField(
            label = stringResource(id = R.string.label_cpf),
            value = userData.cpf,
            handleChange = { onChangeEvent(CadastroField.Cpf(it)) },
            supportingText = stringResource(id = R.string.input_message_error_cpf),
            isError = validations.isCpfInvalido,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            visualTransformation = CpfVisualTransformation()
        )
        InputField(
            label = stringResource(id = R.string.label_data_nascimento),
            value = userData.dataNascimento,
            enabled = false,
            supportingText = stringResource(id = R.string.input_message_error_data_nascimento),
            isError = validations.isDataNascimentoInvalida,
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
            onChangeEvent(CadastroField.DataNascimento(novaData))
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
                                if (userData.genero == it) Azul
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
                                    if (userData.genero == it) Color.White
                                    else Azul
                                    )
                        )
                    }
                }
            }
        }

        ButtonAction(
            label = stringResource(id = R.string.label_button_proximo),
            handleClick = {
                if (!validations.isNomeInvalido &&
                    !validations.isEmailInvalido &&
                    !validations.isCpfInvalido &&
                    !validations.isDataNascimentoInvalida &&
                    userData.genero.isNotBlank()
                ) {
                    onNextClick()
                } else {
                    Toast.makeText(context, "Preencha corretamente os campos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }
}
