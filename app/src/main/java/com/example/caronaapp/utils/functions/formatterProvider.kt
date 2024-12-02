package com.example.caronaapp.utils.functions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertTimestampToLocalTime(timestamp: Long): LocalTime {
    val localTime = Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return localTime
}

fun formatDate(date: LocalDate): String {
    return if (date.isEqual(LocalDate.now())) {
        "Hoje"
    } else if (date.isEqual(LocalDate.now().plusDays(1))) {
        "Amanhã"
    } else if (date.isEqual(LocalDate.now().minusDays(1))) {
        "Ontem"
    } else {
        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}

fun formatTime(time: LocalTime): String {
    return time.format(DateTimeFormatter.ofPattern("HH:mm")) + "h"
}

fun formatCpf(cpf: String): String {
    return cpf.replace(
        "(\\d{3})(\\d{3})(\\d{3})(\\d{2})".toRegex(),
        "$1.$2.$3-$4"
    )
}

fun formatCep(cep: String): String {
    return cep.replace(
        "(\\d{5})(\\d{3})".toRegex(),
        "$1-$2"
    )
}

fun capitalizeWord(word: String): String {
    return word.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}