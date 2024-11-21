package com.example.caronaapp.utils.functions

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun formatDate(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
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