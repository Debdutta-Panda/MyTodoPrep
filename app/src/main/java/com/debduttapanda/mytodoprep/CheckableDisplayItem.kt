package com.debduttapanda.mytodoprep
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.mytodoprep.ui.theme.GreenColor
import com.debduttapanda.mytodoprep.ui.theme.checkable_checked_style
import com.debduttapanda.mytodoprep.ui.theme.checkable_unchecked_style
import com.debduttapanda.mytodoprep.ui.theme.task_title_style
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CheckableDisplayItem(
    task: Task,
    checkable: Checkable,
    vm: HomeViewModel = viewModel()
) {
    var editing by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = checkable.checked,
            onCheckedChange = {checked->
                vm.onCheckableCheck(task,checkable,checked)
            }
        )
        if(!editing){
            if(checkable.checked){
                Text(
                    checkable.value,
                    style = checkable_checked_style,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
            else{
                Text(
                    checkable.value,
                    style = checkable_unchecked_style,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
        else{
            val (focusRequester) = FocusRequester.createRefs()
            LaunchedEffect(
                key1 = editing){
                if(editing){
                    focusRequester.requestFocus()
                }
            }
            BasicTextField(
                value = editingText,
                onValueChange = {
                    editingText = it
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .focusRequester(focusRequester),
                cursorBrush = SolidColor(Color.Red)
            )
        }
        if(!editing){
            IconButton(
                onClick = {
                    editingText = checkable.value
                    editing = true
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
        else{
            IconButton(
                onClick = {
                    editing = false
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Edit",
                    tint = Color.Red
                )
            }
            8.spaceX()
            IconButton(
                onClick = {
                    vm.onCheckableValueChange(task,checkable,editingText)
                    editing = false
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Edit",
                    tint = GreenColor
                )
            }
        }
    }
}