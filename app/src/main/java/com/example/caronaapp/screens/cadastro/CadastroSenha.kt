package com.example.caronaapp.screens.cadastro

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.Olho
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.utils.layout.InputField

@Composable
fun CadastroSenha(
    userData: UserCadastroState,
    onChangeEvent: (CadastroField) -> Unit,
    onSaveClick: () -> Unit,
    validations: UserCadastroValidations
) {
    val context = LocalContext.current

    fun isSenhaValida(): Boolean {
        return (
                validations.senhaContainsMaiuscula &&
                        validations.senhaContainsMinuscula &&
                        validations.senhaContainsNumero &&
                        validations.senhaContainsCaractereEspecial
                )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.defina_sua_senha),
            color = Azul,
            style = MaterialTheme.typography.labelLarge
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = R.string.a_senha_deve_conter),
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (validations.senhaContainsMaiuscula) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (validations.senhaContainsMaiuscula) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (validations.senhaContainsMaiuscula) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.letra_maiuscula),
                    color = if (validations.senhaContainsMaiuscula) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (validations.senhaContainsMinuscula) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (validations.senhaContainsMinuscula) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (validations.senhaContainsMinuscula) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.letra_minuscula),
                    color = if (validations.senhaContainsMinuscula) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (validations.senhaContainsNumero) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (validations.senhaContainsNumero) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (validations.senhaContainsNumero) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.numero),
                    color = if (validations.senhaContainsNumero) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (validations.senhaContainsCaractereEspecial) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (validations.senhaContainsCaractereEspecial) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (validations.senhaContainsCaractereEspecial) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.caracter_especial),
                    color = if (validations.senhaContainsCaractereEspecial) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                label = stringResource(id = R.string.label_senha),
                value = userData.senha,
                handleChange = { onChangeEvent(CadastroField.Senha(it)) },
                visualTransformation = PasswordVisualTransformation(),
                endIcon = Olho,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                label = stringResource(id = R.string.label_confirmacao_senha),
                value = userData.confirmacaoSenha,
                handleChange = { onChangeEvent(CadastroField.ConfirmacaoSenha(it)) },
                visualTransformation = PasswordVisualTransformation(),
                endIcon = Olho
            )
        }

        ButtonAction(
            label = stringResource(id = R.string.label_button_finalizar),
            handleClick = {
                if (!isSenhaValida()) {
                    Toast.makeText(context, "Digite uma senha válida", Toast.LENGTH_SHORT).show()
                } else if (userData.senha != userData.confirmacaoSenha) {
                    Toast.makeText(context, "As senhas devem ser iguais", Toast.LENGTH_SHORT).show()
                } else {
                    onSaveClick()
                }
            }
        )
    }
}