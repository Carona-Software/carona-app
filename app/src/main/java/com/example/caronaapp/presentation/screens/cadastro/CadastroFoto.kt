package com.example.caronaapp.presentation.screens.cadastro

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.utils.layout.ButtonAction

@Composable
fun CadastroFoto(
    userData: UserCadastroState,
    onChangeEvent: (CadastroField) -> Unit,
    onNextClick: () -> Unit
) {
    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        onChangeEvent(CadastroField.Foto(uri!!))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.escolha_sua_foto_de_perfil),
            color = Azul,
            style = MaterialTheme.typography.labelLarge
        )

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (userData.foto == null) {
                Image(
                    painter = painterResource(id = R.mipmap.usuario_selecionar_foto),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(280.dp)
                        .border(2.dp, Azul, CircleShape)
                        .clip(CircleShape)
                        .background(CinzaF5)
                        .scale(0.6f)
                )
            } else {
                AsyncImage(
                    model = userData.foto,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(280.dp)
                        .border(2.dp, Azul, CircleShape)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text(
                    text = stringResource(id = R.string.alterar_foto),
                    color = Azul,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        ButtonAction(
            label = stringResource(id = R.string.label_button_proximo),
            handleClick = {
                if (userData.foto == null) {
                    Toast.makeText(context, "Selecione sua foto de perfil", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    onNextClick()
                }
            }
        )

    }
}