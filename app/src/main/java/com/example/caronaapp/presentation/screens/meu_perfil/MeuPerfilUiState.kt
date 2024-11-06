package com.example.caronaapp.presentation.screens.meu_perfil

import android.net.Uri
import com.example.caronaapp.utils.formatDate
import java.time.LocalDate

sealed class MeuPerfilField {
    data class Nome(val value: String) : MeuPerfilField()
    data class Email(val value: String) : MeuPerfilField()
    data class DataNascimento(val value: LocalDate) : MeuPerfilField()
    data class Foto(val value: Uri) : MeuPerfilField()
    data class EnderecoCep(val value: String) : MeuPerfilField()
    data class EnderecoUf(val value: String) : MeuPerfilField()
    data class EnderecoCidade(val value: String) : MeuPerfilField()
    data class EnderecoBairro(val value: String) : MeuPerfilField()
    data class EnderecoLogradouro(val value: String) : MeuPerfilField()
    data class EnderecoNumero(val value: Int) : MeuPerfilField()
}

data class MeuPerfilState(
    val nome: String,
    val email: String,
    val cpf: String,
    val perfil: String,
    val genero: String,
    val dataNascimento: LocalDate,
    val fotoAtual: String,
    val novaFoto: Uri? = null,
    val enderecoCep: String,
    val enderecoUf: String,
    val enderecoCidade: String,
    val enderecoBairro: String,
    val enderecoLogradouro: String,
    val enderecoNumero: Int,
)

data class MeuPerfilValidations(
    val isNomeInvalido: Boolean = false,
    val isEmailInvalido: Boolean = false,
    val isDataNascimentoInvalida: Boolean = false,
    val isCepInvalido: Boolean = false,
    val isNumeroInvalido: Boolean = false,
)

data class CriterioFeedbackCalculo(
    val notaMedia: Double = 0.0,
    val percentual: Float = 0f
)

data class AvaliacoesCriterioUser(
    val comunicacao: CriterioFeedbackCalculo = CriterioFeedbackCalculo(),
    val seguranca: CriterioFeedbackCalculo = CriterioFeedbackCalculo(),
    val pontualidade: CriterioFeedbackCalculo = CriterioFeedbackCalculo(),
    val comportamento: CriterioFeedbackCalculo = CriterioFeedbackCalculo(),
    val dirigibilidade: CriterioFeedbackCalculo = CriterioFeedbackCalculo()
)