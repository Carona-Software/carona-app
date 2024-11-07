package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.dto.endereco.PrincipalTrajeto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.presentation.screens.meu_perfil.AvaliacoesCriterioUser
import com.example.caronaapp.utils.calculateCriteriosFeedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class PerfilOutroUsuarioViewModel(
    private val usuarioRepository: UsuarioRepositoryImpl
) : ViewModel() {
    val userData = MutableStateFlow<UsuarioDetalhesListagemDto?>(
//        null
        UsuarioDetalhesListagemDto(
            id = 1,
            nome = "Gustavo Medeiros",
            email = "gustavo@email.com",
            cpf = "50378814800",
            genero = "MASCULINO",
            perfil = "MOTORISTA",
            dataNascimento = LocalDate.now(),
            fotoUrl = "https://res.cloudinary.com/carona/image/upload/v1729863605/ph8npbut9xtt2vhg2i0z.png",
            notaMedia = 4.1,
            viagensRealizadas = 1,
            endereco = EnderecoListagemDto(
                id = 1,
                cep = "04244000",
                uf = "SP",
                cidade = "São Paulo",
                logradouro = "Estrada das Lágrimas",
                bairro = "São João CLímaco",
                numero = 3621
            ),
            avaliacoes = listOf(
                FeedbackListagemDto(
                    data = LocalDate.now(),
                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                    avaliador = FeedbackListagemDto.AvaliadorDto(
                        id = 1,
                        nome = "Kaiky Cruz",
                        fotoUrl = "foto_kaiky"
                    ),
                    notaMedia = 4.3,
                    notasCriterios = listOf(
                        NotaCriterioListagemDto(
                            criterio = "Comunicação",
                            nota = 2.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Pontualidade",
                            nota = 5.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Dirigibilidade",
                            nota = 3.5
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Segurança",
                            nota = 4.0
                        )
                    )
                ),
                FeedbackListagemDto(
                    data = LocalDate.now(),
                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                    avaliador = FeedbackListagemDto
                        .AvaliadorDto(
                            id = 1,
                            nome = "Kaiky Cruz",
                            fotoUrl = "foto_kaiky"
                        ),
                    notaMedia = 3.2,
                    notasCriterios = listOf(
                        NotaCriterioListagemDto(
                            criterio = "Comunicação",
                            nota = 4.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Pontualidade",
                            nota = 2.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Dirigibilidade",
                            nota = 5.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Segurança",
                            nota = 1.0
                        )
                    )
                )
            ),
            carros = listOf(
                UsuarioDetalhesListagemDto.CarroDto(
                    marca = "Honda",
                    modelo = "Fit",
                    cor = "Preto",
                    placa = "ABC1D06"
                )
            ),
            fidelizados = listOf(
                FidelizadoListagemDto(
                    id = 2,
                    nome = "Kaiky Cruz",
                    fotoUrl = "foto_kaiky",
                    notaGeral = 3.5,
                    ufLocalidade = "SP",
                    cidadeLocalidade = "São Paulo",
                    qtdViagensJuntos = 1
                )
            ),
            viagens = listOf(
                ViagemSimplesListagemDto(
                    id = 1,
                    data = LocalDate.now(),
                    hora = LocalTime.now(),
                    preco = 30.0,
                    status = StatusViagem.FINALIZADA
                )
            ),
            principalTrajeto = PrincipalTrajeto(
                ufChegada = "SP",
                cidadeChegada = "Campinas",
                ufPartida = "SP",
                cidadePartida = "São Paulo"
            )
        )
    )

    val avaliacoesCriterioUser = MutableStateFlow(AvaliacoesCriterioUser())

    fun getDetalhesUsuario(id: Int) {
        viewModelScope.launch {
            try {
                val response = usuarioRepository.findById(id)

                if (response.isSuccessful) {
                    userData.update { response.body()!! }

                    avaliacoesCriterioUser.update {
                        calculateCriteriosFeedback(userData.value!!.avaliacoes)
                    }
                } else {
                    userData.update { null }
                    Log.e(
                        "perfil outro usuario",
                        "Erro ao buscar informações do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                userData.update { null }
                Log.e(
                    "perfil outro usuario",
                    "Exception -> erro ao buscar informações do usuário: ${e.message}"
                )
            }
        }
    }

    init { // Remover quando integração estiver OK
        avaliacoesCriterioUser.update {
            calculateCriteriosFeedback(userData.value!!.avaliacoes)
        }
    }
}