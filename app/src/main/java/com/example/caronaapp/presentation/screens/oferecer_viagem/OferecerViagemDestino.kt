package com.example.caronaapp.presentation.screens.oferecer_viagem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Localizacao
import com.example.caronaapp.ui.theme.PontoPartida
import com.example.caronaapp.utils.layout.DropdownEnderecoResult

@Composable
fun OferecerViagemDestino(
    modifier: Modifier,
    onDismissPartidaDropdown: () -> Unit,
    onDismissChegadaDropdown: () -> Unit,
    state: OferecerViagemState,
    handleOnChange: (OferecerViagemField) -> Unit,
    handleOnClick: (OferecerViagemEnderecoField) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        DropdownEnderecoResult(
            inputLabel = stringResource(id = R.string.label_ponto_de_partida),
            inputValue = state.pontoPartida,
            startIcon = PontoPartida,
            onChangeEvent = {
                handleOnChange(
                    OferecerViagemField.PontoPartida(it)
                )
            },
            isDropdownExpanded = state.isPartidaDropdownExpanded,
            results = state.resultsPontoPartida,
            onDismissDropdown = { onDismissPartidaDropdown() },
            onDropdownItemClick = { endereco ->
                handleOnClick(
                    OferecerViagemEnderecoField.PontoPartida(endereco)
                )
            }
        )
//        Spacer(modifier = Modifier.height(40.dp))
        DropdownEnderecoResult(
            inputLabel = stringResource(id = R.string.label_ponto_de_chegada),
            inputValue = state.pontoDestino,
            startIcon = Localizacao,
            onChangeEvent = {
                handleOnChange(
                    OferecerViagemField.PontoDestino(it)
                )
            },
            isDropdownExpanded = state.isDestinoDropdownExpanded,
            results = state.resultsPontoDestino,
            onDismissDropdown = { onDismissChegadaDropdown() },
            onDropdownItemClick = { endereco ->
                handleOnClick(
                    OferecerViagemEnderecoField.PontoDestino(endereco)
                )
            }
        )
    }
}