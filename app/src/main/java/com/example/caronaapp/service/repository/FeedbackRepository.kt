package com.example.caronaapp.service.repository

import com.example.caronaapp.data.dto.feedback.FeedbackCriterioCriacaoDto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.entity.CriterioFeedback
import retrofit2.Response

interface FeedbackRepository {

    suspend fun save(feedbackCriterioCriacaoDto: FeedbackCriterioCriacaoDto): Response<FeedbackListagemDto>

    suspend fun findAllCriterios(): Response<List<CriterioFeedback>>

    suspend fun findById(id: Int): Response<FeedbackListagemDto>

    suspend fun findByUsuarioId(id: Int): Response<List<FeedbackListagemDto>>
}