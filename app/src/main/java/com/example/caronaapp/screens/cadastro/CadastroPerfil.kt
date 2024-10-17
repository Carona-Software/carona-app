package com.example.caronaapp.screens.cadastro

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.utils.layout.ButtonAction
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulPerfilSelecionado
import com.example.caronaapp.ui.theme.CinzaDA

@Composable
fun CadastroPerfil(userData: UsuarioCriacaoDto, onClick: (String) -> Unit) {
    val context = LocalContext.current

    var perfil by remember { mutableStateOf(userData.perfil) }

    fun setPerfil(valor: String) {
        if (perfil == valor) perfil = "" else perfil = valor
    }

    fun onButtonClick() {
        if ( perfil != "" ) {
            onClick(perfil)
        } else {
            Toast.makeText(context, "Selecione o seu perfil", Toast.LENGTH_SHORT).show()
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
            text = stringResource(id = R.string.escolha_seu_perfil),
            color = Azul,
            style = MaterialTheme.typography.labelLarge
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    1.dp,
                    if (perfil == "MOTORISTA") Azul
                    else CinzaDA,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .background(
                    if (perfil == "MOTORISTA") AzulPerfilSelecionado
                    else Color.White
                )
                .clickable { setPerfil("MOTORISTA") }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.motorista),
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )
            Image(
                painter = painterResource(id = R.drawable.perfil_motorista),
                contentDescription = "Motorista",
                modifier = Modifier.fillMaxSize(0.9f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    1.dp,
                    if (perfil == "PASSAGEIRO") Azul
                    else CinzaDA,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .background(
                    if (perfil == "PASSAGEIRO") AzulPerfilSelecionado
                    else Color.White
                )
                .clickable { setPerfil("PASSAGEIRO") }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.passageiro),
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )
            Image(
                painter = painterResource(id = R.drawable.perfil_passageiro),
                contentDescription = "Passageiro",
                modifier = Modifier.fillMaxSize(0.9f)
            )
        }

        ButtonAction(
            label = stringResource(id = R.string.label_button_proximo),
            handleClick = { onButtonClick() }
        )

    }
}
        