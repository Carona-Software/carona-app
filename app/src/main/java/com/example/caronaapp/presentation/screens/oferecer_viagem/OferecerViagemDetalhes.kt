package com.example.caronaapp.presentation.screens.oferecer_viagem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Chevron
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.utils.layout.ApenasMulheresSwitch
import com.example.caronaapp.utils.layout.QtdPassageirosField

@Composable
fun OferecerViagemDetalhes(
    modifier: Modifier,
    state: OferecerViagemState,
    handleOnChange: (OferecerViagemField) -> Unit,
    carrosData: List<CarroListagemDto>?,
    onCarrosDropdownClick: () -> Unit,
    onCarroItemClick: (OferecerViagemField) -> Unit,
    onCarrosDismissRequest: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        QtdPassageirosField(
            value = state.capacidadePassageiros.toString(),
            handleAddQnt = {
                if (state.capacidadePassageiros < 4) {
                    handleOnChange(
                        OferecerViagemField.CapacidadePassageiros(
                            state.capacidadePassageiros + 1
                        )
                    )
                }
            },
            handleRemoveQnt = {
                if (state.capacidadePassageiros > 1) {
                    handleOnChange(
                        OferecerViagemField.CapacidadePassageiros(
                            state.capacidadePassageiros - 1
                        )
                    )
                }
            }
        )

        // Preço
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_preco_passageiro),
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier
                    .width(140.dp)
                    .height(52.dp)
                    .border(
                        border = BorderStroke(2.dp, Azul),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cifrao_rs),
                    style = MaterialTheme.typography.labelMedium,
                    color = Azul,
                )
                TextField(
                    value = if (state.preco == 0.0) "" else state.preco.toString(),
                    onValueChange = { novoPreco ->
                        handleOnChange(OferecerViagemField.Preco(novoPreco.toDouble()))
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Azul,
                        unfocusedTextColor = Azul,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledPlaceholderColor = Cinza90,
                        disabledTextColor = Azul
                    ),
                    textStyle = MaterialTheme.typography.headlineMedium,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Column {
            Text(
                text = stringResource(id = R.string.carro),
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .border(
                        border = BorderStroke(2.dp, Azul),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.carro,
                    color = Azul,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                )

                IconButton(
                    onClick = { onCarrosDropdownClick() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Chevron,
                        contentDescription = "Exibir carros",
                        tint = Azul
                    )
                }

                if (carrosData != null) {
                    Box {
                        DropdownMenu(
                            expanded = state.isCarrosDropdownExpanded,
                            onDismissRequest = { onCarrosDismissRequest() },
                            modifier = Modifier.background(Color.White)
                        ) {
                            carrosData.forEach { carro ->
                                DropdownMenuItem(text = {
                                    Text(
                                        text = "${carro.marca} ${carro.modelo}",
                                        color = Azul,
                                        style = MaterialTheme.typography.labelMedium,
                                    )
                                }, onClick = { onCarroItemClick(OferecerViagemField.Carro(carro)) })
                            }
                        }
                    }
                }
            }
        }

        ApenasMulheresSwitch(checked = state.apenasMulheres) {
            handleOnChange(OferecerViagemField.ApenasMulheres(it))
        }
    }
}