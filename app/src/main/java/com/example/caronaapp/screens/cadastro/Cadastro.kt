package com.example.caronaapp.screens.cadastro

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulStepCadastro
import com.example.caronaapp.ui.theme.BrancoF1
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaD9
import com.example.caronaapp.utils.cadastroFactory
import com.example.caronaapp.utils.layout.CustomCard
import com.example.caronaapp.view_models.CadastroViewModel

class CadastroStepClass(
    val label: String,
    val etapa: Int,
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CadastroScreen(navController: NavController) {
    val context = LocalContext.current

    val viewModel = viewModel<CadastroViewModel>(
        factory = cadastroFactory()
    )

    val etapaAtual by viewModel.etapaAtual.collectAsState()

    val userCadastroState by viewModel.userCadastroState.collectAsState()
    val userCadastroValidations by viewModel.userCadastroValidations.collectAsState()

    val isBackToLogin by viewModel.isBackToLogin.collectAsState()
    val isSignUpSuccessful by viewModel.isSignUpSuccessful.collectAsState()

    val isCadastroLoading by viewModel.isCadastroLoading.collectAsState()

    val stepsCadastro = listOf(
        CadastroStepClass(
            label = stringResource(id = R.string.pessoais),
            etapa = 1
        ),
        CadastroStepClass(
            label = stringResource(id = R.string.perfil),
            etapa = 2
        ),
        CadastroStepClass(
            label = stringResource(id = R.string.endereco),
            etapa = 3
        ),
        CadastroStepClass(
            label = stringResource(id = R.string.foto),
            etapa = 4
        ),
        CadastroStepClass(
            label = stringResource(id = R.string.senha),
            etapa = 5
        )
    )

    if (isBackToLogin) {
        navController.popBackStack()
    }

    if (isSignUpSuccessful) {
        navController.navigate("login")
    }

    CaronaAppTheme {
        Column(
            modifier = Modifier
                .background(BrancoF1)
                .fillMaxSize()
                .padding(
                    top = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { viewModel.onBackClick() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Voltar",
                        tint = Azul
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.cadastro),
                color = Azul,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    )
                    .height(30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                stepsCadastro.forEach { cadastroStep ->
                    CadastroStep(
                        label = cadastroStep.label,
                        etapa = cadastroStep.etapa,
                        etapaAtual = etapaAtual
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            CustomCard {
                when (etapaAtual) {
                    1 -> CadastroPessoais(
                        userData = userCadastroState,
                        onNextClick = { viewModel.onNextClick() },
                        onChangeEvent = { viewModel.onChangeEvent(it) },
                        validations = userCadastroValidations
                    )

                    2 -> CadastroPerfil(
                        userData = userCadastroState,
                        onNextClick = { viewModel.onNextClick() },
                        onChangeEvent = { viewModel.onChangeEvent(it) }
                    )

                    3 -> CadastroEndereco(
                        userData = userCadastroState,
                        onNextClick = { viewModel.onNextClick() },
                        onChangeEvent = { viewModel.onChangeEvent(it) },
                        handleSearchCep = { viewModel.handleSearchCep() },
                        validations = userCadastroValidations
                    )

                    4 -> CadastroFoto(
                        userData = userCadastroState,
                        onNextClick = { viewModel.onNextClick() },
                        onChangeEvent = { viewModel.onChangeEvent(it) }
                    )

                    5 -> CadastroSenha(
                        userData = userCadastroState,
                        onSaveClick = { viewModel.onSignUpClick(context) },
                        onChangeEvent = { viewModel.onChangeEvent(it) },
                        validations = userCadastroValidations,
                        isLoading = isCadastroLoading
                    )
                }
            }
        }
    }
}

@Composable
fun CadastroStep(
    label: String,
    etapa: Int,
    etapaAtual: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(76.dp)
            .padding(
                start = 4.dp,
                end = 4.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = (
                    if (etapa <= etapaAtual) Azul
                    else AzulStepCadastro
                    ),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (etapa <= etapaAtual) Azul
                    else CinzaD9
                )
        ) {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun CadastroScreenPreview() {
    CaronaAppTheme {
        CadastroScreen(rememberNavController())
    }
}