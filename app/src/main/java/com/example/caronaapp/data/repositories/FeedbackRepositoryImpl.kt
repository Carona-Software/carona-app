package com.example.caronaapp.data.repositories

import com.example.caronaapp.data.dto.feedback.FeedbackConsultaDto
import com.example.caronaapp.data.dto.feedback.FeedbackCriterioCriacaoDto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.entity.CriterioFeedback
import com.example.caronaapp.service.api.FeedbackApi
import com.example.caronaapp.service.repository.FeedbackRepository
import retrofit2.Response

class FeedbackRepositoryImpl(private val feedbackApi: FeedbackApi) : FeedbackRepository {
    override suspend fun save(feedbackCriterioCriacaoDto: FeedbackCriterioCriacaoDto): Response<FeedbackListagemDto> {
        return feedbackApi.save(feedbackCriterioCriacaoDto)
    }

    override suspend fun findAllCriterios(): Response<List<CriterioFeedback>> {
        return feedbackApi.findAllCriterios()
    }

    override suspend fun findById(id: Int): Response<FeedbackListagemDto> {
        return feedbackApi.findById(id)
    }

    override suspend fun findByUsuarioId(id: Int): Response<List<FeedbackListagemDto>> {
        return feedbackApi.findByUsuarioId(id)
    }

    override suspend fun existsByDestinatarioAndRemetenteAndViagem(feedbackConsultaDto: FeedbackConsultaDto): Response<Boolean> {
        return feedbackApi.existsByDestinatarioAndRemetenteAndViagem(
            feedbackConsultaDto.destinatarioId,
            feedbackConsultaDto.remetenteId,
            feedbackConsultaDto.viagemId
        )
    }
}