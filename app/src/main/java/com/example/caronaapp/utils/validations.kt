package com.example.caronaapp.utils

fun isEmailValid(email: String): Boolean {
    return email.isNotBlank() && (
            !email.contains("@") ||
                    !email.contains(".") ||
                    email.length < 8)
}

fun isCpfValido(cpf: String): Boolean {
    return cpf.isNotBlank() && !cpfExists(cpf)
}

private fun cpfExists(cpf: String): Boolean {
    val cpfFormatado = cpf.filter { it.isDigit() }

    if (cpfFormatado.length != 11) return false

    if (cpfFormatado.all { it == cpfFormatado[0] }) return false

    // Função para calcular o primeiro e segundo dígito verificador
    fun calcularDigito(cpf: String, factor: Int): Int {
        var soma = 0
        cpf.forEachIndexed { index, c ->
            soma += c.toString().toInt() * (factor - index)
        }
        val resto = soma % 11
        return if (resto < 2) 0 else 11 - resto
    }

    val primeiroDigito = calcularDigito(cpfFormatado.substring(0, 9), 10)

    val segundoDigito = calcularDigito(cpfFormatado.substring(0, 9) + primeiroDigito, 11)

    return cpfFormatado[9].toString().toInt() == primeiroDigito && cpfFormatado[10].toString()
        .toInt() == segundoDigito
}

fun senhaContainsMaiuscula(senha: String): Boolean {
    return senha.any { it.isUpperCase() }
}

fun senhaContainsMinuscula(senha: String): Boolean {
    return senha.any { it.isLowerCase() }
}

fun senhaContainsNumero(senha: String): Boolean {
    return senha.any { it.isDigit() }
}

fun senhaContainsCaractereEspecial(senha: String): Boolean {
    return senha.any { !it.isLetterOrDigit() }
}

fun isNomeValido(nome: String): Boolean {
    return nome.isNotBlank() && nome.length < 5
}

fun isCepValido(cep: String): Boolean {
    return cep.isNotBlank() && cep.length != 8
}

fun isNumeroValido(numero: Int): Boolean {
    return numero <= 0
}