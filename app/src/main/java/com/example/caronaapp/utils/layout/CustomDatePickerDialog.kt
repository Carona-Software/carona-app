package com.example.caronaapp.utils.layout

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Cinza90
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun CustomDatePickerDialog(
    dialogState: MaterialDialogState,
    allowedDateValidator: (LocalDate) -> Boolean,
    onDateChange: (LocalDate) -> Unit
) {
    MaterialDialog(
        dialogState = dialogState,
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
        this.datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(id = R.string.title_date_picker_dialog),
            allowedDateValidator = { allowedDateValidator(it) },
            colors = DatePickerDefaults.colors(
                calendarHeaderTextColor = Azul,
                dateActiveTextColor = Color.White,
                headerBackgroundColor = Azul,
                dateInactiveTextColor = Azul,
                headerTextColor = Color.White,
                dateActiveBackgroundColor = Azul,
                
            )
        ) {
            onDateChange(it)
        }
    }
}