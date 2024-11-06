package com.example.caronaapp.service.api

import com.example.caronaapp.data.dto.feedback.FeedbackCriterioCriacaoDto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.entity.CriterioFeedback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedbackApi {

    @POST("feedbacks")
    suspend fun save(@Body feedbackCriterioCriacaoDto: FeedbackCriterioCriacaoDto): Response<FeedbackListagemDto>

    @GET("feedbacks/criterios")
    suspend fun findAllCriterios(): Response<List<CriterioFeedback>>

    @GET("feedbacks/{id}")
    suspend fun findById(@Path("id") id: Int): Response<FeedbackListagemDto>

    @GET("feedbacks/usuario/{id}")
    suspend fun findByUsuarioId(@Path("id") id: Int): Response<List<FeedbackListagemDto>>

}