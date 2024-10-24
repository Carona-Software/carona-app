package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.screens.cadastro.CadastroField
import com.example.caronaapp.screens.cadastro.UserCadastroState
import com.example.caronaapp.screens.cadastro.UserCadastroValidations
import com.example.caronaapp.service.repository.CloudinaryRepository
import com.example.caronaapp.service.repository.UsuarioRepository
import com.example.caronaapp.service.repository.ViaCepRepository
import com.example.caronaapp.utils.formatDate
import com.example.caronaapp.utils.isCpfValido
import com.example.caronaapp.utils.isEmailValid
import com.example.caronaapp.utils.senhaContainsCaractereEspecial
import com.example.caronaapp.utils.senhaContainsMaiuscula
import com.example.caronaapp.utils.senhaContainsMinuscula
import com.example.caronaapp.utils.senhaContainsNumero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CadastroViewModel(
    val usuarioRepository: UsuarioRepository,
    val viaCepRepository: ViaCepRepository,
    val cloudinaryRepository: CloudinaryRepository
) :
    ViewModel() {

    val userCadastroData = MutableStateFlow(UsuarioCriacaoDto())
    val userCadastroState = MutableStateFlow(UserCadastroState())
    val userCadastroValidations = MutableStateFlow(UserCadastroValidations())

    var etapaAtual = MutableStateFlow(1)
        private set

    var isSignUpSuccessful = MutableStateFlow(false)
        private set

    var isBackToLogin = MutableStateFlow(false)

    fun onChangeEvent(field: CadastroField) {
        when (field) {
            is CadastroField.Nome -> {
                userCadastroData.update { it.copy(nome = field.value) }
                userCadastroState.update { it.copy(nome = field.value) }
                userCadastroValidations.update { it.copy(isNomeInvalido = (isNomeValido(field.value))) }
            }

            is CadastroField.Email -> {
                userCadastroData.update { it.copy(email = field.value) }
                userCadastroState.update { it.copy(email = field.value) }
                userCadastroValidations.update { it.copy(isEmailInvalido = (isEmailValid(field.value))) }
            }

            is CadastroField.Cpf -> {
                userCadastroData.update { it.copy(cpf = field.value) }
                userCadastroState.update { it.copy(cpf = field.value) }
                userCadastroValidations.update { it.copy(isCpfInvalido = isCpfValido(field.value)) }
            }

            is CadastroField.DataNascimento -> {
                userCadastroData.update { it.copy(dataNascimento = field.value) }
                userCadastroState.update { it.copy(dataNascimento = formatDate(field.value)) }
            }

            is CadastroField.Genero -> {
                userCadastroData.update { it.copy(genero = field.value) }
                userCadastroState.update { it.copy(genero = field.value) }
            }

            is CadastroField.Perfil -> {
                userCadastroData.update { it.copy(perfil = field.value) }
                userCadastroState.update { it.copy(perfil = field.value) }
            }

            is CadastroField.Foto -> {
                userCadastroState.update { it.copy(foto = field.value) }
            }

            is CadastroField.Senha -> {
                userCadastroData.update { it.copy(senha = field.value) }
                userCadastroState.update { it.copy(senha = field.value) }
                userCadastroValidations.update {
                    it.copy(
                        senhaContainsMaiuscula = senhaContainsMaiuscula(field.value),
                        senhaContainsMinuscula = senhaContainsMinuscula(field.value),
                        senhaContainsNumero = senhaContainsNumero(field.value),
                        senhaContainsCaractereEspecial = senhaContainsCaractereEspecial(field.value),
                    )
                }
            }

            is CadastroField.ConfirmacaoSenha -> {
                userCadastroState.update { it.copy(confirmacaoSenha = field.value) }
            }

            is CadastroField.EnderecoCep -> {
                userCadastroData.update {
                    it.copy(endereco = userCadastroData.value.endereco?.copy(cep = field.value))
                }
                userCadastroState.update { it.copy(enderecoCep = field.value) }
                userCadastroValidations.update { it.copy(isCepInvalido = isCepValido(field.value)) }
            }

            is CadastroField.EnderecoUf -> {
                userCadastroData.update {
                    it.copy(endereco = userCadastroData.value.endereco?.copy(uf = field.value))
                }
                userCadastroState.update { it.copy(enderecoUf = field.value) }
            }

            is CadastroField.EnderecoCidade -> {
                userCadastroData.update {
                    it.copy(endereco = userCadastroData.value.endereco?.copy(cidade = field.value))
                }
                userCadastroState.update { it.copy(enderecoCidade = field.value) }
            }

            is CadastroField.EnderecoBairro -> {
                userCadastroData.update {
                    it.copy(endereco = userCadastroData.value.endereco?.copy(bairro = field.value))
                }
                userCadastroState.update { it.copy(enderecoBairro = field.value) }
            }

            is CadastroField.EnderecoLogradouro -> {
                userCadastroData.update {
                    it.copy(endereco = userCadastroData.value.endereco?.copy(logradouro = field.value))
                }
                userCadastroState.update { it.copy(enderecoLogradouro = field.value) }
            }

            is CadastroField.EnderecoNumero -> {
                userCadastroData.update {
                    it.copy(endereco = userCadastroData.value.endereco?.copy(numero = field.value))
                }
                userCadastroState.update { it.copy(enderecoNumero = field.value) }
                userCadastroValidations.update { it.copy(isNumeroInvalido = isNumeroValido(field.value)) }
            }
        }
    }

    fun onBackClick() {
        when (etapaAtual.value) {
            5 -> etapaAtual.update { 4 }
            4 -> etapaAtual.update { 3 }
            3 -> etapaAtual.update { 2 }
            2 -> etapaAtual.update { 1 }
            1 -> isBackToLogin.update { true }
        }
    }

    fun onNextClick() {
        when (etapaAtual.value) {
            1 -> etapaAtual.update { 2 }
            2 -> etapaAtual.update { 3 }
            3 -> etapaAtual.update { 4 }
            4 -> etapaAtual.update { 5 }
        }
    }

    fun onSignUpClick() {
        uploadFoto()
        viewModelScope.launch {
            try {
                val response = usuarioRepository.post(userCadastroData.value)

                if (response.isSuccessful) {
                    isSignUpSuccessful.value = true
                } else {
                    Log.e("cadastro", "Erro ao cadastrar usuário: ${response.errorBody()}")
                }

            } catch (e: Exception) {
                Log.e("cadastro", "Erro ao cadastrar usuário: ${e.message}")
            }
        }
    }

    private fun isNomeValido(nome: String): Boolean {
        return nome.isNotBlank() && nome.length < 5
    }

    fun isCepValido(cep: String): Boolean {
        return cep.isNotBlank() && cep.length != 8
    }

    fun isNumeroValido(numero: Int): Boolean {
        return numero <= 0
    }

    fun handleSearchCep() {
        viewModelScope.launch {
            try {
                val getEndereco = viaCepRepository.getEndereco(userCadastroState.value.enderecoCep)

                if (getEndereco.isSuccessful) {
                    Log.i("viacep", "Resposta: ${getEndereco.body()}")
                    if (getEndereco.body()?.uf != null) {
                        userCadastroData.update {
                            it.copy(
                                endereco = it.endereco?.copy(
                                    uf = getEndereco.body()?.uf.toString(),
                                    cidade = getEndereco.body()?.cidade.toString(),
                                    bairro = getEndereco.body()?.bairro.toString(),
                                    logradouro = getEndereco.body()?.logradouro.toString(),
                                )
                            )
                        }
                        userCadastroState.update {
                            it.copy(
                                enderecoUf = getEndereco.body()?.uf.toString(),
                                enderecoCidade = getEndereco.body()?.cidade.toString(),
                                enderecoBairro = getEndereco.body()?.bairro.toString(),
                                enderecoLogradouro = getEndereco.body()?.logradouro.toString(),

                                )
                        }
                    } else {
                        userCadastroValidations.update { it.copy(isCepInvalido = true) }
                        userCadastroData.update {
                            it.copy(
                                endereco = it.endereco?.copy(
                                    uf = "",
                                    cidade = "",
                                    bairro = "",
                                    logradouro = "",
                                )
                            )
                        }
                        userCadastroState.update {
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
                    userCadastroValidations.update { it.copy(isCepInvalido = true) }
                }

            } catch (e: Exception) {
                Log.e(
                    "viacep",
                    "Erro ao buscar CEP ${userCadastroState.value.enderecoCep}: ${e.message}"
                )

            }
        }
    }

    fun uploadFoto() {
        viewModelScope.launch {
            try {
                // Converter URI para arquivo
                val file = userCadastroState.value.foto!!.path?.let { File(it) }
                val requestFile = file!!.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                val uploadPreset = "profile_pictures"

                val response = cloudinaryRepository.upload(body, uploadPreset)

                if (response.isSuccessful) {
                    userCadastroData.update { it.copy(fotoUrl = response.body()!!.url) }
                    Log.i(
                        "uploadFoto",
                        "Sucesso ao fazer upload de foto pro Cloudinary: ${response.errorBody()}"
                    )
                } else {
                    Log.e(
                        "uploadFoto",
                        "Erro ao fazer upload de foto pro Cloudinary: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("uploadFoto", "Erro ao fazer upload de foto pro Cloudinary: ${e.message}")
            }
        }
    }
}
