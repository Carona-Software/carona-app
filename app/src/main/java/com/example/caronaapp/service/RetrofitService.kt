package com.example.caronaapp.service

import com.example.caronaapp.service.repository.CaronaRepository
import com.example.caronaapp.service.repository.CarroRepository
import com.example.caronaapp.service.repository.CloudinaryRepository
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
    private val BASE_URL_CLOUDINARY = "https://api.cloudinary.com/v1_1/carona/image/upload"
    private val BASE_URL_CARONA = "https://viacep.com.br/ws/" // ALTERAR

    fun getApi(endpoint: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // VIACEP
    fun getApiViaCepRepository(): ViaCepRepository {
        return getApi(BASE_URL_VIACEP).create(ViaCepRepository::class.java)
    }

    // CLOUDINARY
    fun getApiCloudinaryRepository(): CloudinaryRepository {
        return getApi(BASE_URL_CLOUDINARY).create(CloudinaryRepository::class.java)
    }

    // CARONA
    fun getApiUsuario(): UsuarioRepository {
        return getApi(BASE_URL_CARONA).create(UsuarioRepository::class.java)
    }

    fun getApiViagem(): ViagemRepository {
        return getApi(BASE_URL_CARONA).create(ViagemRepository::class.java)
    }

    fun getApiCarro(): CarroRepository {
        return getApi(BASE_URL_CARONA).create(CarroRepository::class.java)
    }

    fun getApiFeedback(): FeedbackRepository {
        return getApi(BASE_URL_CARONA).create(FeedbackRepository::class.java)
    }

    fun getApiCarona(): CaronaRepository {
        return getApi(BASE_URL_CARONA).create(CaronaRepository::class.java)
    }

    fun getApiFidelizacao(): FidelizacaoRepository {
        return getApi(BASE_URL_CARONA).create(FidelizacaoRepository::class.java)
    }

    fun getApiSolicitacaoFidelizacao(): SolicitacaoFidelizacaoRepository {
        return getApi(BASE_URL_CARONA).create(SolicitacaoFidelizacaoRepository::class.java)
    }

    fun getApiSolicitacaoViagem(): SolicitacaoViagemRepository {
        return getApi(BASE_URL_CARONA).create(SolicitacaoViagemRepository::class.java)
    }
}