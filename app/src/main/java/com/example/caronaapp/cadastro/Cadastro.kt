package com.example.caronaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.cadastro.CadastroEndereco
import com.example.caronaapp.cadastro.CadastroFoto
import com.example.caronaapp.cadastro.CadastroPessoais
import com.example.caronaapp.cadastro.CadastroPerfil
import com.example.caronaapp.cadastro.CadastroSenha
import com.example.caronaapp.data_class.Usuario
import com.example.caronaapp.layout.CustomCard
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulStepCadastro
import com.example.caronaapp.ui.theme.BrancoF1
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.CinzaD9

class Cadastro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroScreen()
        }
    }
}

class CadastroStepClass(
    val label: String,
    val etapa: Int,
)

@Composable
fun CadastroScreen() {
    val context = LocalContext.current
    var etapaAtual by remember { mutableStateOf(1) }

    val user = Usuario().copy()

    fun handlePessoaisClick(
        nome: String,
        email: String,
        cpf: String,
        genero: String,
        dataNascimento: String
    ) {
        user.nome = nome
        user.email = email
        user.cpf = cpf
        user.genero = genero
        user.dataNascimento = dataNascimento

        etapaAtual = 2
    }

    fun handlePerfilClick(perfil: String) {
        user.perfil = perfil
        etapaAtual = 3
    }

    fun handleEnderecoClick(
        cep: String,
        uf: String,
        cidade: String,
        bairro: String,
        logradouro: String,
        numero: Int
    ) {
        user.endereco?.cep ?: cep
        user.endereco?.uf ?: uf
        user.endereco?.cidade ?: cidade
        user.endereco?.bairro ?: bairro
        user.endereco?.logradouro ?: logradouro
        user.endereco?.numero ?: numero

        etapaAtual = 4
    }

    fun handleFotoClick(foto: String) {
        user.foto = foto
        etapaAtual = 5
    }

    fun handleSenhaClick(senha: String) {
        user.senha = senha
        val login = Intent(context, Login::class.java)
        context.startActivity(login)
    }

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

    fun onBackClick() {
        when (etapaAtual) {
            5 -> etapaAtual = 4
            4 -> etapaAtual = 3
            3 -> etapaAtual = 2
            2 -> etapaAtual = 1
            else -> {}
        }
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
                IconButton(onClick = { onBackClick() }) {
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
                        userData = user,
                        onClick = { nome, email, cpf, genero, dataNascimento ->
                            handlePessoaisClick(nome, email, cpf, genero, dataNascimento)
                        })

                    2 -> CadastroPerfil(userData = user, onClick = { perfil ->
                        handlePerfilClick(perfil = perfil)
                    })

                    3 ->
                        CadastroEndereco(
                            enderecoData = user.endereco,
                            onClick = { cep, uf, cidade, bairro, logradouro, numero ->
                                    handleEnderecoClick(cep, uf, cidade, bairro, logradouro, numero)
                            })


                    4 -> CadastroFoto(userData = user, onClick = { etapaAtual = 5 })

                    else -> CadastroSenha(userData = user, onClick = { senha ->
                        handleSenhaClick(senha)
                    })
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

@Preview(showSystemUi = true)
@Composable
fun PreviewCadastroScreen() {
    CaronaAppTheme {
        CadastroScreen()
    }
}