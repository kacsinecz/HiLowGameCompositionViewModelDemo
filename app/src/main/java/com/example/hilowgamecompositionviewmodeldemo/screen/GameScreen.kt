@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hilowgamecompositionviewmodeldemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hilowgamecompositionviewmodeldemo.R


@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text= stringResource(R.string.text_enter_guess))},
            value = gameViewModel.numberText,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal ) ,
            onValueChange = {
                gameViewModel.numberText = it
                gameViewModel.validate(context.getString(R.string.error_empty))
            },
            isError = gameViewModel.inputErrorState, trailingIcon = {
                if(gameViewModel.inputErrorState) {
                    Icon(Icons.Filled.Warning,"error",tint = MaterialTheme.colorScheme.error)
                }
            })

        Button(onClick = {
            try {
                gameViewModel.increaseCounter()

                val myNum = gameViewModel.numberText.toInt()

                if (myNum == gameViewModel.generatedNum) {
                    gameViewModel.textResult = "You have won!"
                    gameViewModel.showWinDialog = true
                } else if (myNum < gameViewModel.generatedNum) {
                    gameViewModel.textResult = "The number is larger"
                } else  {
                    gameViewModel.textResult = "The number is smaller"
                }
            } catch(e:Exception) {
                gameViewModel.textResult = e.message.toString()
            }

        },
            enabled = !gameViewModel.inputErrorState) {
            Text(text = "Guess(${gameViewModel.counter})")
        }

        Text(text=gameViewModel.textResult,
            fontSize=28.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Blue
        )

        SimpleAlertDialog(
            show = gameViewModel.showWinDialog,
            onDismiss = {gameViewModel.showWinDialog = false},
            onConfirm = {gameViewModel.showWinDialog = false}
        )

        Button(onClick = {
            gameViewModel.generateNewNum()
        }) {
            Text(text = stringResource(R.string.restart))
        }
    }
}

@Composable fun SimpleAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
)
{
    if(show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text= stringResource(R.string.congratulatinos)) },
            text = { Text(text= stringResource(R.string.you_have_fun)) },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text (text= stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text (text= stringResource(R.string.cancel))
                }
            }
        )

    }

}


