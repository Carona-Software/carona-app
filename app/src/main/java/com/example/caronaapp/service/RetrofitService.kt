package com.example.caronaapp.service

import com.example.caronaapp.service.interfaces.ApiViaCep
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitService {
    private val BASE_URL_VIACEP = "https://viacep.com.br/ws/"

    fun getApiViaCep(): ApiViaCep {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_VIACEP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}