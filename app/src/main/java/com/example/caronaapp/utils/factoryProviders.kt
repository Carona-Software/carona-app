package com.example.caronaapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.caronaapp.service.RetrofitService
import com.example.caronaapp.view_models.AvaliacoesViewModel
import com.example.caronaapp.view_models.CadastroViewModel
import com.example.caronaapp.view_models.CarrosViewModel
import com.example.caronaapp.view_models.DetalhesViagemViewModel
import com.example.caronaapp.view_models.FeedbackViewModel
import com.example.caronaapp.view_models.FidelizadosViewModel
import com.example.caronaapp.view_models.LoginViewModel
import com.example.caronaapp.view_models.MeuPerfilViewModel
import com.example.caronaapp.view_models.OferecerViagemViewModel
import com.example.caronaapp.view_models.ProcurarViagemViewModel
import com.example.caronaapp.view_models.SolicitacaoFidelizacaoViewModel
import com.example.caronaapp.view_models.SolicitacaoViagemViewModel
import com.example.caronaapp.view_models.HistoricoViagensViewModel
import retrofit2.Retrofit

fun avaliacoesFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return AvaliacoesViewModel(
            repository = RetrofitService.getApiFeedback()
        ) as T
    }
}

fun cadastroFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return CadastroViewModel(
            usuarioRepository = RetrofitService.getApiUsuario(),
            viaCepRepository = RetrofitService.getApiViaCepRepository(),
            cloudinaryRepository = RetrofitService.getApiCloudinaryRepository()
        ) as T
    }
}

fun carrosFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return CarrosViewModel(
            repository = RetrofitService.getApiCarro()
        ) as T
    }
}

fun detalhesViagemFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return DetalhesViagemViewModel(
            viagemRepository = RetrofitService.getApiViagem(),
            solicitacaoViagemRepository = RetrofitService.getApiSolicitacaoViagem(),
            caronaRepository = RetrofitService.getApiCarona()
        ) as T
    }
}

fun feedbackFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return FeedbackViewModel(
            repository = RetrofitService.getApiFeedback()
        ) as T
    }
}

fun fidelizadosFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return FidelizadosViewModel(
            fidelizacaoRepository = RetrofitService.getApiFidelizacao(),
            solicitacaoFidelizacaoRepository = RetrofitService.getApiSolicitacaoFidelizacao()
        ) as T
    }
}

fun loginFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            repository = RetrofitService.getApiUsuario()
        ) as T
    }
}

fun meuPerfilFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MeuPerfilViewModel(
            usuarioRepository = RetrofitService.getApiUsuario(),
            viaCepRepository = RetrofitService.getApiViaCepRepository()
        ) as T
    }
}

fun oferecerViagemFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return OferecerViagemViewModel(
            viagemRepository = RetrofitService.getApiViagem()
        ) as T
    }
}

fun procurarViagemFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return ProcurarViagemViewModel(
            viagemRepository = RetrofitService.getApiViagem()
        ) as T
    }
}

fun solicitacaoFidelizacaoFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return SolicitacaoFidelizacaoViewModel(
            repository = RetrofitService.getApiSolicitacaoFidelizacao()
        ) as T
    }
}

fun solicitacaoViagemFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return SolicitacaoViagemViewModel(
            repository = RetrofitService.getApiSolicitacaoViagem()
        ) as T
    }
}

fun viagensHistoricoFactory() = object : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return HistoricoViagensViewModel(
            repository = RetrofitService.getApiViagem()
        ) as T
    }
}