package com.example.caronaapp.service.retrofit

import com.example.caronaapp.service.repository.CaronaRepository
import com.example.caronaapp.service.repository.CarroRepository
import com.example.caronaapp.service.repository.FeedbackRepository
import com.example.caronaapp.service.repository.FidelizacaoRepository
import com.example.caronaapp.service.repository.SolicitacaoFidelizacaoRepository
import com.example.caronaapp.service.repository.SolicitacaoViagemRepository
import com.example.caronaapp.service.repository.UsuarioRepository
import com.example.caronaapp.service.repository.ViaCepRepository
import com.example.caronaapp.service.repository.ViagemRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val BASE_URL_VIACEP = "https://viacep.com.br/ws/"
    private val BASE_URL_CARONA = ""

    // VIACEP
    fun getApiViaCep(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_VIACEP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiViaCepRepository(): ViaCepRepository {
        return getApiViaCep().create(ViaCepRepository::class.java)
    }

    // CARONA
    fun getApiCaronaApp(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_CARONA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiUsuario(): UsuarioRepository {
        return getApiCaronaApp().create(UsuarioRepository::class.java)
    }

    fun getApiViagem(): ViagemRepository {
        return getApiCaronaApp().create(ViagemRepository::class.java)
    }

    fun getApiCarro(): CarroRepository {
        return getApiCaronaApp().create(CarroRepository::class.java)
    }

    fun getApiFeedback(): FeedbackRepository {
        return getApiCaronaApp().create(FeedbackRepository::class.java)
    }

    fun getApiCarona(): CaronaRepository {
        return getApiCaronaApp().create(CaronaRepository::class.java)
    }

    fun getApiFidelizacao(): FidelizacaoRepository {
        return getApiCaronaApp().create(FidelizacaoRepository::class.java)
    }

    fun getApiSolicitacaoFidelizacao(): SolicitacaoFidelizacaoRepository {
        return getApiCaronaApp().create(SolicitacaoFidelizacaoRepository::class.java)
    }

    fun getApiSolicitacaoViagem(): SolicitacaoViagemRepository {
        return getApiCaronaApp().create(SolicitacaoViagemRepository::class.java)
    }
}