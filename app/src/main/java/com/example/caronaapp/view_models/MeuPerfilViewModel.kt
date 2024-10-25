package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.dto.endereco.PrincipalTrajeto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.screens.meu_perfil.AvaliacoesCriterioUser
import com.example.caronaapp.screens.meu_perfil.CriterioFeedbackCalculo
import com.example.caronaapp.screens.meu_perfil.MeuPerfilField
import com.example.caronaapp.screens.meu_perfil.MeuPerfilState
import com.example.caronaapp.screens.meu_perfil.MeuPerfilValidations
import com.example.caronaapp.service.repository.UsuarioRepository
import com.example.caronaapp.service.repository.ViaCepRepository
import com.example.caronaapp.utils.isCepValido
import com.example.caronaapp.utils.isEmailValid
import com.example.caronaapp.utils.isNomeValido
import com.example.caronaapp.utils.isNumeroValido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class MeuPerfilViewModel(
    val usuarioRepository: UsuarioRepository,
    val viaCepRepository: ViaCepRepository,
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
    val meuPerfilState = MutableStateFlow(
        MeuPerfilState(
            nome = userData.value?.nome ?: "",
            email = userData.value?.email ?: "",
            cpf = userData.value?.cpf ?: "",
            perfil = userData.value?.perfil ?: "",
            genero = userData.value?.genero ?: "",
            dataNascimento = userData.value?.dataNascimento ?: LocalDate.now(),
            fotoAtual = userData.value?.fotoUrl ?: "",
            enderecoCep = userData.value?.endereco?.cep ?: "",
            enderecoUf = userData.value?.endereco?.uf ?: "",
            enderecoCidade = userData.value?.endereco?.cidade ?: "",
            enderecoBairro = userData.value?.endereco?.bairro ?: "",
            enderecoLogradouro = userData.value?.endereco?.logradouro ?: "",
            enderecoNumero = userData.value?.endereco?.numero ?: 0
        )
    )

    val validations = MutableStateFlow(MeuPerfilValidations())

//    var solicitacoesViagem = MutableStateFlow<List<SolicitacaoViagemListagemDto>?>(null)
//        private set
//
//    var solicitacoesFidelizacao = MutableStateFlow<List<SolicitacaoFidelizacaoListagemDto>?>(null)
//        private set

    // Variáveis de erro
    var isNomeDialogEnabled = MutableStateFlow(false)
        private set

    var isEmailDialogEnabled = MutableStateFlow(false)
        private set

    var isDataNascimentoDialogEnabled = MutableStateFlow(false)
        private set

    var isFotoDialogEnabled = MutableStateFlow(false)
        private set

    var isEnderecoDialogEnabled = MutableStateFlow(false)
        private set

    fun getDetalhesUsuario(id: Int) {
        viewModelScope.launch {
            try {
                val response = usuarioRepository.findById(id)

                if (response.isSuccessful) {
                    userData.value = response.body()!!
                    calculateAndRenderCriteriosFeedback()
                } else {
                    userData.value = null
                    Log.e(
                        "meu perfil",
                        "Erro ao buscar informações do usuário: ${response.errorBody()?.toString()}"
                    )
                }
            } catch (e: Exception) {
                userData.value = null
                Log.e("meu perfil", "Erro ao buscar informações do usuário: ${e.message}")
            }
        }
    }

    private fun calculateTotalPorcentageCriterio(notaMedia: Double): Float {
        return ((notaMedia / 5 * 100) / 100).toFloat()
    }

    private fun calculateAndRenderCriteriosFeedback() {
        if (userData.value!!.avaliacoes.isNotEmpty()) {
            var somaTotalComunicacao = 0.0
            var somaTotalSeguranca = 0.0
            var somaTotalComportamento = 0.0
            var somaTotalPontualidade = 0.0
            var somaTotalDirigibilidade = 0.0

            userData.value!!.avaliacoes.forEach { avaliacao ->
                avaliacao.notasCriterios.forEach { criterio ->
                    when (criterio.criterio) {
                        "Comunicação" -> somaTotalComunicacao += criterio.nota
                        "Segurança" -> somaTotalSeguranca += criterio.nota
                        "Comportamento" -> somaTotalComportamento += criterio.nota
                        "Pontualidade" -> somaTotalPontualidade += criterio.nota
                        "Dirigibilidade" -> somaTotalDirigibilidade += criterio.nota
                    }
                }
            }

            val size = userData.value!!.avaliacoes.size

            val notaMediaComunicacao = somaTotalComunicacao / size
            val notaMediaSeguranca = somaTotalSeguranca / size
            val notaMediaComportamento = somaTotalComportamento / size
            val notaMediaPontualidade = somaTotalPontualidade / size
            val notaMediaDirigibilidade = somaTotalDirigibilidade / size

            val percentualComunicacao =
                calculateTotalPorcentageCriterio(somaTotalComunicacao / size)
            val percentualSeguranca = calculateTotalPorcentageCriterio(somaTotalSeguranca / size)
            val percentualComportamento =
                calculateTotalPorcentageCriterio(somaTotalComportamento / size)
            val percentualPontualidade =
                calculateTotalPorcentageCriterio(somaTotalPontualidade / size)
            val percentualDirigibilidade =
                calculateTotalPorcentageCriterio(somaTotalDirigibilidade / size)

            avaliacoesCriterioUser.update {
                it.copy(
                    comunicacao = CriterioFeedbackCalculo(
                        notaMedia = notaMediaComunicacao,
                        percentual = percentualComunicacao
                    ),
                    seguranca = CriterioFeedbackCalculo(
                        notaMedia = notaMediaSeguranca,
                        percentual = percentualSeguranca
                    ),
                    pontualidade = CriterioFeedbackCalculo(
                        notaMedia = notaMediaPontualidade,
                        percentual = percentualPontualidade
                    ),
                    comportamento = CriterioFeedbackCalculo(
                        notaMedia = notaMediaComportamento,
                        percentual = percentualComportamento
                    ),
                    dirigibilidade = CriterioFeedbackCalculo(
                        notaMedia = notaMediaDirigibilidade,
                        percentual = percentualDirigibilidade
                    ),
                )
            }
        }
    }

    fun onChangeEvent(field: MeuPerfilField) {
        when (field) {
            is MeuPerfilField.Nome -> {
                meuPerfilState.update { it.copy(nome = field.value) }
                validations.update { it.copy(isNomeInvalido = (isNomeValido(field.value))) }
            }

            is MeuPerfilField.Email -> {
                meuPerfilState.update { it.copy(email = field.value) }
                validations.update { it.copy(isEmailInvalido = (isEmailValid(field.value))) }
            }

            is MeuPerfilField.Foto -> {
                meuPerfilState.update { it.copy(novaFoto = field.value) }
            }

            is MeuPerfilField.DataNascimento -> {
                meuPerfilState.update { it.copy(dataNascimento = field.value) }
            }

            is MeuPerfilField.EnderecoCep -> {
                meuPerfilState.update { it.copy(enderecoCep = field.value) }
                validations.update { it.copy(isCepInvalido = isCepValido(field.value)) }
            }

            is MeuPerfilField.EnderecoUf -> {
                meuPerfilState.update { it.copy(enderecoUf = field.value) }
            }

            is MeuPerfilField.EnderecoCidade -> {
                meuPerfilState.update { it.copy(enderecoCidade = field.value) }
            }

            is MeuPerfilField.EnderecoBairro -> {
                meuPerfilState.update { it.copy(enderecoBairro = field.value) }
            }

            is MeuPerfilField.EnderecoLogradouro -> {
                meuPerfilState.update { it.copy(enderecoLogradouro = field.value) }
            }

            is MeuPerfilField.EnderecoNumero -> {
                meuPerfilState.update { it.copy(enderecoNumero = field.value) }
                validations.update { it.copy(isNumeroInvalido = isNumeroValido(field.value)) }
            }
        }
    }

    fun onOpenModalClick(context: String) {
        when (context.uppercase()) {
            "NOME" -> isNomeDialogEnabled.update { true }
            "EMAIL" -> isEmailDialogEnabled.update { true }
            "DATA DE NASCIMENTO" -> isDataNascimentoDialogEnabled.update { true }
            "FOTO" -> isFotoDialogEnabled.update { true }
            "ENDEREÇO" -> isEnderecoDialogEnabled.update { true }
        }
    }

    fun onDismissModalClick(context: String) {
        when (context.uppercase()) {
            "NOME" -> {
                meuPerfilState.update { it.copy(nome = userData.value!!.nome) }
                isNomeDialogEnabled.update { false }
            }

            "EMAIL" -> {
                meuPerfilState.update { it.copy(email = userData.value!!.nome) }
                isEmailDialogEnabled.update { false }
            }

            "DATA DE NASCIMENTO" -> {
                meuPerfilState.update { it.copy(dataNascimento = userData.value!!.dataNascimento) }
                isDataNascimentoDialogEnabled.update { false }
            }

            "FOTO" -> {
                meuPerfilState.update { it.copy(novaFoto = null) }
                isFotoDialogEnabled.update { false }
            }

            "ENDEREÇO" -> {
                meuPerfilState.update {
                    it.copy(
                        enderecoCep = userData.value!!.endereco.cep,
                        enderecoUf = userData.value!!.endereco.uf,
                        enderecoCidade = userData.value!!.endereco.cidade,
                        enderecoBairro = userData.value!!.endereco.bairro,
                        enderecoLogradouro = userData.value!!.endereco.logradouro,
                        enderecoNumero = userData.value!!.endereco.numero,
                    )
                }
                isEnderecoDialogEnabled.update { false }
            }
        }
    }

    fun updateUserData(id: Int) {
        viewModelScope.launch {
            try {
                val userToUpdate = UsuarioCriacaoDto(
                    nome = userData.value!!.nome,
                    email = userData.value!!.email,
                    cpf = userData.value!!.cpf,
                    genero = userData.value!!.genero,
                    perfil = userData.value!!.perfil,
                    dataNascimento = userData.value!!.dataNascimento,
                    fotoUrl = meuPerfilState.value.fotoAtual,
                    endereco = EnderecoCriacaoDto(
                        id = userData.value!!.endereco.id,
                        cep = userData.value!!.endereco.cep,
                        uf = userData.value!!.endereco.uf,
                        cidade = userData.value!!.endereco.cidade,
                        bairro = userData.value!!.endereco.bairro,
                        logradouro = userData.value!!.endereco.logradouro,
                        numero = userData.value!!.endereco.numero,
                    ),
                )

                val response = usuarioRepository.update(id, userToUpdate)

                if (response.isSuccessful) {
                    userData.value = response.body()
                } else {
                    userData.value = null
                    Log.e(
                        "meu perfil",
                        "Erro ao atualizar usuário: ${response.errorBody()?.toString()}"
                    )
                }
            } catch (e: Exception) {
                userData.value = null
                Log.e("meu perfil", "Erro ao atualizar usuário: ${e.printStackTrace()}")
            }
        }
    }

    fun handleSearchCep() {
        viewModelScope.launch {
            try {
                val getEndereco = viaCepRepository.getEndereco(meuPerfilState.value.enderecoCep)

                if (getEndereco.isSuccessful) {
                    Log.i("viacep", "Resposta: ${getEndereco.body()}")
                    if (getEndereco.body()?.uf != null) {
                        meuPerfilState.update {
                            it.copy(
                                enderecoUf = getEndereco.body()?.uf.toString(),
                                enderecoCidade = getEndereco.body()?.cidade.toString(),
                                enderecoBairro = getEndereco.body()?.bairro.toString(),
                                enderecoLogradouro = getEndereco.body()?.logradouro.toString(),
                            )
                        }
                    } else {
                        validations.update { it.copy(isCepInvalido = true) }
                        meuPerfilState.update {
                            it.copy(
                                enderecoUf = "",
                                enderecoCidade = "",
                                enderecoBairro = "",
                                enderecoLogradouro = "",
                            )
                        }
                    }
                } else {
                    Log.e(
                        "viacep",
                        "Erro na resposta: ${getEndereco.errorBody()!!.string()}"
                    )
                    validations.update { it.copy(isCepInvalido = true) }
                }

            } catch (e: Exception) {
                Log.e(
                    "viacep",
                    "Erro ao buscar CEP ${meuPerfilState.value.enderecoCep}: ${e.printStackTrace()}"
                )

            }
        }
    }
}
