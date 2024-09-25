package com.example.caronaapp.utils.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.Cinza90

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
    saveButtonLabel: String,
    saveButtonColor: Color,
    onSaveClick: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(
                        text = stringResource(id = R.string.label_button_cancelar),
                        color = Cinza90,
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = { onSaveClick() },
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = saveButtonColor,
                    )
                ) {
                    Text(
                        text = saveButtonLabel,
                        color = Color.White,
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
            }
        }
    }
}

