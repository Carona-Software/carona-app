package com.example.caronaapp.features.cadastro

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.data.Usuario
import com.example.caronaapp.layout.ButtonAction
import com.example.caronaapp.layout.InputField
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.Olho

@Composable
fun CadastroSenha(userData: Usuario, onClick: (String) -> Unit) {
    val context = LocalContext.current

    var senha by remember { mutableStateOf(userData.senha) }
    var confirmacaoSenha by remember { mutableStateOf("") }

    var containsMaiuscula by remember { mutableStateOf(false) }
    var containsMinuscula by remember { mutableStateOf(false) }
    var containsNumero by remember { mutableStateOf(false) }
    var containsCaracterEspecial by remember { mutableStateOf(false) }

    fun onSenhaChange(it: String) {
        if (it != " ") {
            senha = it
            containsMaiuscula = senhaContainsMaiuscula(it)
            containsMinuscula = senhaContainsMinuscula(it)
            containsNumero = senhaContainsNumero(it)
            containsCaracterEspecial = senhaContainsCaracterEspecial(it)
        }
    }

    fun isSenhaValida(): Boolean {
        return containsMaiuscula && containsMinuscula && containsNumero && containsCaracterEspecial
    }

    fun onButtonClick() {
        if (!isSenhaValida()) {
            Toast.makeText(context, "Digite uma senha válida", Toast.LENGTH_SHORT).show()
        } else if (!isConfirmacaoSenhaValido(senha, confirmacaoSenha)) {
            Toast.makeText(context, "As senhas devem ser iguais", Toast.LENGTH_SHORT).show()
        } else {
            onClick(senha)
        }
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
                    if (containsMaiuscula) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (containsMaiuscula) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (containsMaiuscula) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.letra_maiuscula),
                    color = if (containsMaiuscula) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (containsMinuscula) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (containsMinuscula) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (containsMinuscula) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.letra_minuscula),
                    color = if (containsMinuscula) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (containsNumero) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (containsNumero) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (containsNumero) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.numero),
                    color = if (containsNumero) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (containsCaracterEspecial) Icons.Default.Done else Icons.Default.Circle,
                    tint = if (containsCaracterEspecial) Azul else Cinza90,
                    contentDescription = null,
                    modifier = Modifier.size(
                        if (containsCaracterEspecial) 24.dp else 12.dp
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.caracter_especial),
                    color = if (containsCaracterEspecial) Azul else Cinza90,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                label = stringResource(id = R.string.label_senha),
                value = senha,
                handleChange = { onSenhaChange(it) },
                visualTransformation = PasswordVisualTransformation(),
                endIcon = Olho
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                label = stringResource(id = R.string.label_confirmacao_senha),
                value = confirmacaoSenha,
                handleChange = { confirmacaoSenha = it },
                visualTransformation = PasswordVisualTransformation(),
                endIcon = Olho
            )
        }

        ButtonAction(
            label = stringResource(id = R.string.label_button_finalizar),
            handleClick = { onButtonClick() }
        )
    }
}

private fun senhaContainsMaiuscula(senha: String): Boolean {
    return senha.any { it.isUpperCase() }
}

private fun senhaContainsMinuscula(senha: String): Boolean {
    return senha.any { it.isLowerCase() }
}

private fun senhaContainsNumero(senha: String): Boolean {
    return senha.any { it.isDigit() }
}

private fun senhaContainsCaracterEspecial(senha: String): Boolean {
    return senha.any { !it.isLetterOrDigit() }
}

private fun isConfirmacaoSenhaValido(senha: String, confirmacaoSenha: String): Boolean {
    return confirmacaoSenha == senha
}