package com.example.caronaapp.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.repositories.CloudinaryRepositoryImpl
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.data.repositories.ViaCepRepositoryImpl
import com.example.caronaapp.presentation.screens.cadastro.CadastroField
import com.example.caronaapp.presentation.screens.cadastro.UserCadastroState
import com.example.caronaapp.presentation.screens.cadastro.UserCadastroValidations
import com.example.caronaapp.utils.functions.configPhotoToUpload
import com.example.caronaapp.utils.functions.isCepValido
import com.example.caronaapp.utils.functions.isCpfValido
import com.example.caronaapp.utils.functions.isEmailValid
import com.example.caronaapp.utils.functions.isNomeValido
import com.example.caronaapp.utils.functions.isNumeroValido
import com.example.caronaapp.utils.functions.senhaContainsCaractereEspecial
import com.example.caronaapp.utils.functions.senhaContainsMaiuscula
import com.example.caronaapp.utils.functions.senhaContainsMinuscula
import com.example.caronaapp.utils.functions.senhaContainsNumero
import com.example.caronaapp.utils.functions.setPasswordVisibility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class CadastroViewModel(
    private val usuarioRepository: UsuarioRepositoryImpl,
    private val viaCepRepository: ViaCepRepositoryImpl,
    private val cloudinaryRepository: CloudinaryRepositoryImpl,
) : ViewModel() {

    private val userCadastroData = MutableStateFlow(UsuarioCriacaoDto())
    val userCadastroState = MutableStateFlow(UserCadastroState())
    val userCadastroValidations = MutableStateFlow(UserCadastroValidations())

    val etapaAtual = MutableStateFlow(1)

    val isSignUpSuccessful = MutableStateFlow(false)

    val isBackToLogin = MutableStateFlow(false)

    val isCadastroLoading = MutableStateFlow(false)

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
                userCadastroData.update {
                    it.copy(
                        dataNascimento = field.value.format(DateTimeFormatter.ISO_LOCAL_DATE)
                    )
                }
                userCadastroState.update { it.copy(dataNascimento = field.value) }
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
                    it.copy(endereco = it.endereco.copy(cep = field.value))
                }
                userCadastroState.update { it.copy(enderecoCep = field.value) }
                userCadastroValidations.update { it.copy(isCepInvalido = isCepValido(field.value)) }
            }

            is CadastroField.EnderecoNumero -> {
                userCadastroData.update {
                    it.copy(endereco = it.endereco.copy(numero = field.value))
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

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)
    val state = _state.asStateFlow()

    fun onSignUpClick(context: Context) {
        viewModelScope.launch {
            Log.i("cadastro", "Cadastro Dto: ${userCadastroData.value}")
            isCadastroLoading.update { true }
            uploadFotoUser(context)
            try {
                val response = usuarioRepository.post(userCadastroData.value)

                if (response.isSuccessful) {
                    _state.value = SignUpState.Loading

                    val auth = FirebaseAuth.getInstance()
                    val firestore = FirebaseFirestore.getInstance()

                    auth.createUserWithEmailAndPassword(userCadastroData.value.email, userCadastroData.value.senha)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                task.result.user?.let { user ->
                                    user.updateProfile(userProfileChangeRequest {
                                        displayName = userCadastroData.value.nome
                                    }).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val userData = mapOf(
                                                "userId" to user.uid,
                                                "nome" to userCadastroData.value.nome,
                                                "email" to userCadastroData.value.email,
                                                "status" to "offline",
                                                "fotoUrl" to userCadastroData.value.fotoUrl,
                                                "perfil" to userCadastroData.value.perfil
                                            )
                                            firestore.collection("users")
                                                .document(user.uid)
                                                .set(userData)
                                                .addOnSuccessListener {
                                                    _state.value = SignUpState.Success
                                                }
                                                .addOnFailureListener {
                                                    _state.value = SignUpState.Error
                                                }
                                        } else {
                                            _state.value = SignUpState.Error
                                        }
                                    }
                                }
                            }
                        }

                    isSignUpSuccessful.update { true }
                    Log.i("cadastro", "Sucesso ao cadastrar usuário: ${response.body()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("cadastro", "Erro ao cadastrar usuário: ${response.code()}")
                    Log.e("cadastro", "Erro ao cadastrar usuário: $errorBody")
                    Log.e("cadastro", "Erro ao cadastrar usuário: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("cadastro", "Erro ao cadastrar usuário: ${e.printStackTrace()}")
            } finally {
                isCadastroLoading.update { false }
            }
        }
    }

    private suspend fun uploadFotoUser(context: Context) {
        try {
            val bodyUploadDto = configPhotoToUpload(context, userCadastroState.value.foto!!)

            val response = cloudinaryRepository.upload(
                bodyUploadDto.cloudName,
                bodyUploadDto.body,
                bodyUploadDto.uploadPreset,
                bodyUploadDto.apiKey,
                bodyUploadDto.timestamp,
                bodyUploadDto.signature,
            )

            if (response.isSuccessful) {
                Log.i(
                    "cloudinary",
                    "Sucesso ao fazer upload de foto pro Cloudinary: ${response.body()}"
                )
                userCadastroData.update { it.copy(fotoUrl = response.body()!!.url) }
            } else {
                Log.e(
                    "cloudinary",
                    "Erro ao fazer upload de foto pro Cloudinary: ${response.errorBody()}"
                )
//                userCadastroData.update { it.copy(fotoUrl = "") }
            }
        } catch (e: Exception) {
            Log.e(
                "cloudinary",
                "Exception -> erro ao fazer upload de foto pro Cloudinary: ${e.printStackTrace()}"
            )
        }
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
                                endereco = it.endereco.copy(
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
                                endereco = it.endereco.copy(
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

    fun setSignUpSuccessfulToFalse() {
        isSignUpSuccessful.update { false }
    }

    fun setBackToLoginToFalse() {
        isBackToLogin.update { false }
    }

    fun setPasswordVisibility() {
        userCadastroValidations.update {
            it.copy(
                showPassword = setPasswordVisibility(userCadastroValidations.value.showPassword)
            )
        }
    }

    fun setConfirmationPasswordVisibility() {
        userCadastroValidations.update {
            it.copy(
                showConfirmationPassword = setPasswordVisibility(userCadastroValidations.value.showConfirmationPassword)
            )
        }
    }
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    object Error : SignUpState()
}
