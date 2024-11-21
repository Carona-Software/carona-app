package com.example.caronaapp.presentation.screens.carros

import androidx.compose.ui.graphics.Color
import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosMarcaResponse
import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosModeloResponse
import com.example.caronaapp.ui.theme.AmareloComboBox
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.BrancoF1
import com.example.caronaapp.ui.theme.CinzaComboBox
import com.example.caronaapp.ui.theme.LaranjaComboBox
import com.example.caronaapp.ui.theme.MarromComboBox
import com.example.caronaapp.ui.theme.PrataComboBox
import com.example.caronaapp.ui.theme.RoxoComboBox
import com.example.caronaapp.ui.theme.VerdeComboBox
import com.example.caronaapp.ui.theme.VermelhoComboBox
import com.example.caronaapp.ui.theme.VinhoComboBox

sealed class CarroField {
    data class Marca(val value: String) : CarroField()
    data class Modelo(val value: String) : CarroField()
    data class Placa(val value: String) : CarroField()
    data class Cor(val value: String) : CarroField()
}

data class CorCarro(
    val label: String,
    val cor: Color
)

data class CarrosUiState(
    val carros: List<CarroListagemDto>? = null,
    val isCreateDialogOpened: Boolean = false,
    val isEditDialogOpened: Boolean = false,
    val isDeleteDialogOpened: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String = "",
    val successMessage: String = "",
    val isMarcasDropdownExpanded: Boolean = false,
    val isModelosDropdownExpanded: Boolean = false,
    val isCoresDropdownExpanded: Boolean = false,
    val marcasCarroData: List<String> = emptyList(),
    val modelosCarroData: List<String> = emptyList(),
    val coresCarro: List<CorCarro> = listOf(
        CorCarro(label = "Preto", cor = Color.Black),
        CorCarro(label = "Branco", cor = BrancoF1),
        CorCarro(label = "Azul", cor = Azul),
        CorCarro(label = "Verde", cor = VerdeComboBox),
        CorCarro(label = "Vermelho", cor = VermelhoComboBox),
        CorCarro(label = "Roxo", cor = RoxoComboBox),
        CorCarro(label = "Marrom", cor = MarromComboBox),
        CorCarro(label = "Laranja", cor = LaranjaComboBox),
        CorCarro(label = "Cinza", cor = CinzaComboBox),
        CorCarro(label = "Amarelo", cor = AmareloComboBox),
        CorCarro(label = "Prata", cor = PrataComboBox),
        CorCarro(label = "Vinho", cor = VinhoComboBox)
    ),
    val createCarroData: CarroCriacaoDto = CarroCriacaoDto(),
    val editCarroData: CarroCriacaoDto = CarroCriacaoDto(),
    val deleteCarroData: CarroListagemDto? = null
)