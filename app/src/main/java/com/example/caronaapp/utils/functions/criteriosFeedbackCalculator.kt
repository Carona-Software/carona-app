package com.example.caronaapp.utils.functions

import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.presentation.screens.meu_perfil.AvaliacoesCriterioUser
import com.example.caronaapp.presentation.screens.meu_perfil.CriterioFeedbackCalculo

fun calculateCriteriosFeedback(avaliacoes: List<FeedbackListagemDto>): AvaliacoesCriterioUser {
    if (avaliacoes.isNotEmpty()) {
        var somaTotalComunicacao = 0.0
        var somaTotalSeguranca = 0.0
        var somaTotalComportamento = 0.0
        var somaTotalPontualidade = 0.0
        var somaTotalDirigibilidade = 0.0

        avaliacoes.forEach { avaliacao ->
            avaliacao.notasCriterios.forEach { criterio ->
                when (criterio.criterio) {
                    "Comunicação" -> somaTotalComunicacao += criterio.nota
                    "Segurança" -> somaTotalSeguranca += criterio.nota
                    "Comportamento" -> somaTotalComportamento += criterio.nota
                    "Pontualidade" -> somaTotalPontualidade += criterio.nota
                    "Dirigibilidade" -> somaTotalDirigibilidade += criterio.nota
                }
            }
        }

        val size = avaliacoes.size

        val notaMediaComunicacao = somaTotalComunicacao / size
        val notaMediaSeguranca = somaTotalSeguranca / size
        val notaMediaComportamento = somaTotalComportamento / size
        val notaMediaPontualidade = somaTotalPontualidade / size
        val notaMediaDirigibilidade = somaTotalDirigibilidade / size

        val percentualComunicacao =
            calculateTotalPorcentageCriterio(somaTotalComunicacao / size)
        val percentualSeguranca = calculateTotalPorcentageCriterio(somaTotalSeguranca / size)
        val percentualComportamento =
            calculateTotalPorcentageCriterio(somaTotalComportamento / size)
        val percentualPontualidade =
            calculateTotalPorcentageCriterio(somaTotalPontualidade / size)
        val percentualDirigibilidade =
            calculateTotalPorcentageCriterio(somaTotalDirigibilidade / size)

        return AvaliacoesCriterioUser(
            comunicacao = CriterioFeedbackCalculo(
                notaMedia = notaMediaComunicacao,
                percentual = percentualComunicacao
            ),
            seguranca = CriterioFeedbackCalculo(
                notaMedia = notaMediaSeguranca,
                percentual = percentualSeguranca
            ),
            pontualidade = CriterioFeedbackCalculo(
                notaMedia = notaMediaPontualidade,
                percentual = percentualPontualidade
            ),
            comportamento = CriterioFeedbackCalculo(
                notaMedia = notaMediaComportamento,
                percentual = percentualComportamento
            ),
            dirigibilidade = CriterioFeedbackCalculo(
                notaMedia = notaMediaDirigibilidade,
                percentual = percentualDirigibilidade
            ),
        )
    } else {
        return AvaliacoesCriterioUser()
    }
}

private fun calculateTotalPorcentageCriterio(notaMedia: Double): Float {
    return ((notaMedia / 5 * 100) / 100).toFloat()
}
