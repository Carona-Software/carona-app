package com.example.caronaapp.presentation.screens.oferecer_viagem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Calendario
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5
import com.example.caronaapp.ui.theme.Horario
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.functions.formatTime
import com.example.caronaapp.utils.layout.CustomDatePickerDialog
import com.example.caronaapp.utils.layout.InputField
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun OferecerViagemHorario(
    modifier: Modifier,
    state: OferecerViagemState,
    handleOnChange: (OferecerViagemField) -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        InputField(
            label = stringResource(id = R.string.label_dia),
            value = formatDate(state.data),
            startIcon = Calendario,
            onIconClick = {
                dateDialogState.show()
            },
            enabled = false
        )

        CustomDatePickerDialog(
            dialogState = dateDialogState,
            allowedDateValidator = {
                !it.isBefore(LocalDate.now())
            },
            selectedDate = state.data,
            onDateChange = { novaData ->
                handleOnChange(OferecerViagemField.Data(novaData))
            }
        )

        InputField(
            label = stringResource(id = R.string.horario),
            value = formatTime(state.hora),
            startIcon = Horario,
            onIconClick = {
                timeDialogState.show()
            },
            enabled = false
        )

        // TimePicker
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(
                    text = "Ok",
                    TextStyle(
                        color = Azul,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                negativeButton(
                    text = "Cancelar",
                    TextStyle(
                        color = Cinza90,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            },
            shape = RoundedCornerShape(16.dp),
            properties = DialogProperties(dismissOnBackPress = true)
        ) {
            this.timepicker(
                initialTime = LocalTime.now(),
                title = stringResource(id = R.string.title_time_picker_dialog),
                colors = TimePickerDefaults.colors(
                    activeBackgroundColor = Azul,
                    headerTextColor = Azul,
                    activeTextColor = Color.White,
                    inactiveTextColor = Azul,
                    selectorColor = Azul,
                    selectorTextColor = Color.White,
                    inactivePeriodBackground = Color.White,
                    inactiveBackgroundColor = CinzaF5,
                )
            ) { novoHorario ->
                handleOnChange(OferecerViagemField.HorarioPartida(novoHorario))
            }
        }
    }
}