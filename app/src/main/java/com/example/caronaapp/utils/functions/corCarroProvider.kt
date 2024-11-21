package com.example.caronaapp.utils.functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.caronaapp.R

@Composable
fun returnCorCarro(cor: String): Painter {
    return when (cor) {
        "Amarelo" -> painterResource(id = R.drawable.carro_amarelo)
        "Azul" -> painterResource(id = R.drawable.carro_azul)
        "Branco" -> painterResource(id = R.drawable.carro_branco)
        "Cinza" -> painterResource(id = R.drawable.carro_cinza)
        "Laranja" -> painterResource(id = R.drawable.carro_laranja)
        "Marrom" -> painterResource(id = R.drawable.carro_marrom)
        "Prata" -> painterResource(id = R.drawable.carro_prata)
        "Preto" -> painterResource(id = R.drawable.carro_preto)
        "Roxo" -> painterResource(id = R.drawable.carro_roxo)
        "Verde" -> painterResource(id = R.drawable.carro_verde)
        "Vermelho" -> painterResource(id = R.drawable.carro_vermelho)
        else -> painterResource(id = R.drawable.carro_vinho)
    }
}