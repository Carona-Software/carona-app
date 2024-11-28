package com.example.caronaapp.di

import android.util.Log
import com.example.caronaapp.data.repositories.CaronaRepositoryImpl
import com.example.caronaapp.data.repositories.CarroRepositoryImpl
import com.example.caronaapp.data.repositories.CloudinaryRepositoryImpl
import com.example.caronaapp.data.repositories.FeedbackRepositoryImpl
import com.example.caronaapp.data.repositories.FidelizacaoRepositoryImpl
import com.example.caronaapp.data.repositories.GoogleMapsRepositoryImpl
import com.example.caronaapp.data.repositories.MapboxRepositoryImpl
import com.example.caronaapp.data.repositories.SolicitacaoFidelizacaoRepositoryImpl
import com.example.caronaapp.data.repositories.SolicitacaoViagemRepositoryImpl
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.data.repositories.ViaCepRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.data.repositories.VpicCarrosRepositoryImpl
import com.example.caronaapp.presentation.view_models.AvaliacoesViewModel
import com.example.caronaapp.presentation.view_models.CadastroViewModel
import com.example.caronaapp.presentation.view_models.CarrosViewModel
import com.example.caronaapp.presentation.view_models.DetalhesViagemViewModel
import com.example.caronaapp.presentation.view_models.FeedbackViewModel
import com.example.caronaapp.presentation.view_models.FidelizadosViewModel
import com.example.caronaapp.presentation.view_models.HistoricoViagensViewModel
import com.example.caronaapp.presentation.view_models.LoginViewModel
import com.example.caronaapp.presentation.view_models.MapaViagemViewModel
import com.example.caronaapp.presentation.view_models.MeuPerfilViewModel
import com.example.caronaapp.presentation.view_models.OferecerViagemViewModel
import com.example.caronaapp.presentation.view_models.OnboardingViewModel
import com.example.caronaapp.presentation.view_models.PerfilOutroUsuarioViewModel
import com.example.caronaapp.presentation.view_models.ProcurarViagemViewModel
import com.example.caronaapp.presentation.view_models.ViagensViewModel
import com.example.caronaapp.service.api.CaronaApi
import com.example.caronaapp.service.api.CarroApi
import com.example.caronaapp.service.api.CloudinaryApi
import com.example.caronaapp.service.api.FeedbackApi
import com.example.caronaapp.service.api.FidelizacaoApi
import com.example.caronaapp.service.api.GoogleMapsApi
import com.example.caronaapp.service.api.MapboxApi
import com.example.caronaapp.service.api.SolicitacaoFidelizacaoApi
import com.example.caronaapp.service.api.SolicitacaoViagemApi
import com.example.caronaapp.service.api.UsuarioApi
import com.example.caronaapp.service.api.ViaCepApi
import com.example.caronaapp.service.api.ViagemApi
import com.example.caronaapp.service.api.VpicCarrosApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_VIACEP = "https://viacep.com.br/ws/"
const val BASE_URL_CLOUDINARY = "https://api.cloudinary.com/v1_1/"
const val BASE_URL_GOOGLE_MAPS = "https://maps.googleapis.com/maps/api/"
const val BASE_URL_MAPBOX = "https://api.mapbox.com/directions/v5/mapbox/driving/"
const val BASE_URL_VPIC_CARROS = "https://vpic.nhtsa.dot.gov/api/vehicles/"
const val BASE_URL_CARONA = "http://34.194.147.50:80/api/"

fun logginInterceptor(): HttpLoggingInterceptor {
    Log.d("logginInterceptor", "Iniciando logginInterceptor")

    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

val apiModule = module {

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .cache(null)
            .addInterceptor(logginInterceptor())
            .build()
    }

    // ViaCep
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_VIACEP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ViaCepApi::class.java)
    }

    // Cloudinary
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CLOUDINARY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApi::class.java)
    }

    // GoogleMaps
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_GOOGLE_MAPS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleMapsApi::class.java)
    }

    // Mapbox
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_MAPBOX)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(MapboxApi::class.java)
    }

    // vPIC Carros
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_VPIC_CARROS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
            .create(VpicCarrosApi::class.java)
    }

    // Back-end
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CARONA)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
                .build()
    }

    single { get<Retrofit>().create(CaronaApi::class.java) }
    single { get<Retrofit>().create(CarroApi::class.java) }
    single { get<Retrofit>().create(FeedbackApi::class.java) }
    single { get<Retrofit>().create(FidelizacaoApi::class.java) }
    single { get<Retrofit>().create(SolicitacaoFidelizacaoApi::class.java) }
    single { get<Retrofit>().create(SolicitacaoViagemApi::class.java) }
    single { get<Retrofit>().create(UsuarioApi::class.java) }
    single { get<Retrofit>().create(ViagemApi::class.java) }
}

val repositoryModule = module {
    single { CaronaRepositoryImpl(get()) }
    single { CarroRepositoryImpl(get()) }
    single { CloudinaryRepositoryImpl(get()) }
    single { FeedbackRepositoryImpl(get()) }
    single { FidelizacaoRepositoryImpl(get()) }
    single { GoogleMapsRepositoryImpl(get()) }
    single { MapboxRepositoryImpl(get()) }
    single { SolicitacaoViagemRepositoryImpl(get()) }
    single { SolicitacaoFidelizacaoRepositoryImpl(get()) }
    single { UsuarioRepositoryImpl(get()) }
    single { ViaCepRepositoryImpl(get()) }
    single { ViagemRepositoryImpl(get()) }
    single { VpicCarrosRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { OnboardingViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { CadastroViewModel(get(), get(), get()) }
    viewModel { AvaliacoesViewModel(get(), get()) }
    viewModel { CarrosViewModel(get(), get(), get()) }
    viewModel { DetalhesViagemViewModel(get(), get(), get(), get(), get()) }
    viewModel { FeedbackViewModel(get(), get(), get()) }
    viewModel { FidelizadosViewModel(get(), get(), get()) }
    viewModel { HistoricoViagensViewModel(get(), get()) }
    viewModel { MeuPerfilViewModel(get(), get(), get()) }
    viewModel { MapaViagemViewModel(get(), get()) }
    viewModel { OferecerViagemViewModel(get(), get(), get(), get(), get()) }
    viewModel { PerfilOutroUsuarioViewModel(get(), get(), get(), get(), get()) }
    viewModel { ProcurarViagemViewModel(get(), get()) }
    viewModel { ViagensViewModel(get(), get(), get()) }
}

val dataStoreModule = module {
    single { DataStoreManager(get()) }
}
