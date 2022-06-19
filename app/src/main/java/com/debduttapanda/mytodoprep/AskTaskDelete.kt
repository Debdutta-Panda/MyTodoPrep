package com.debduttapanda.mytodoprep
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.debduttapanda.mytodoprep.ui.theme.LightRedColor
import com.debduttapanda.mytodoprep.ui.theme.dialog_message_style
import com.debduttapanda.mytodoprep.ui.theme.delete_dialog_title_style
import kotlinx.coroutines.launch
@Composable
fun AskTaskDelete(
    vm: HomeViewModel = viewModel()
) {
    Dialog(
        onDismissRequest = {
            vm.onAskTaskDeleteCancel()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    stringResource(id = R.string.delete_task),
                    style = delete_dialog_title_style,
                )
                12.spaceY()
                Divider()
                12.spaceY()
                Text(
                    stringResource(id = R.string.are_you_sure_to_delete),
                    style = dialog_message_style
                )
                12.spaceY()
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Button(
                        onClick = {
                            vm.confirmDeleteTask()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = LightRedColor,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        Text(stringResource(id = R.string.yes))
                    }
                    12.spaceX()
                    Button(
                        onClick = {
                            vm.onAskTaskDeleteCancel()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = LightRedColor
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    ) {
                        Text(stringResource(id = R.string.no))
                    }
                }
            }
        }
    }
}