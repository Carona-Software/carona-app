package com.example.caronaapp.presentation.screens.cadastro

import android.net.Uri
import com.example.caronaapp.utils.functions.formatDate
import java.time.LocalDate


sealed class CadastroField {
    data class Nome(val value: String) : CadastroField()
    data class Email(val value: String) : CadastroField()
    data class Cpf(val value: String) : CadastroField()
    data class Genero(val value: String) : CadastroField()
    data class Perfil(val value: String) : CadastroField()
    data class DataNascimento(val value: LocalDate) : CadastroField()
    data class Senha(val value: String) : CadastroField()
    data class ConfirmacaoSenha(val value: String) : CadastroField()
    data class Foto(val value: Uri) : CadastroField()
    data class EnderecoCep(val value: String) : CadastroField()
    data class EnderecoNumero(val value: Int) : CadastroField()
}

data class UserCadastroState(
    val nome: String = "",
    val email: String = "",
    val cpf: String = "",
    val perfil: String = "",
    val genero: String = "",
    val dataNascimento: String = formatDate(LocalDate.now()),
    val foto: Uri? = null,
    val senha: String = "",
    val confirmacaoSenha: String = "",
    val enderecoCep: String = "",
    val enderecoUf: String = "",
    val enderecoCidade: String = "",
    val enderecoBairro: String = "",
    val enderecoLogradouro: String = "",
    val enderecoNumero: Int = 0,
)

data class UserCadastroValidations(
    val isNomeInvalido: Boolean = false,
    val isEmailInvalido: Boolean = false,
    val isCpfInvalido: Boolean = false,
    val isDataNascimentoInvalida: Boolean = false,
    val isCepInvalido: Boolean = false,
    val isNumeroInvalido: Boolean = false,
    val showPassword: Boolean = false,
    val showConfirmationPassword: Boolean = false,
    val senhaContainsMaiuscula: Boolean = false,
    val senhaContainsMinuscula: Boolean = false,
    val senhaContainsNumero: Boolean = false,
    val senhaContainsCaractereEspecial: Boolean = false
)