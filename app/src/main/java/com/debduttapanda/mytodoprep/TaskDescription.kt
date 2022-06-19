package com.debduttapanda.mytodoprep
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TaskDescription(
    vm: AddOrEditTaskViewModel = viewModel()
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = vm.description.value,
        onValueChange = {
            vm.onDescriptionChange(it)
        },
        placeholder = {
            Text(
                stringResource(id = R.string.new_task_description_placeholder),
            )
        },
        label = {
            Text(
                stringResource(id = R.string.new_task_description_placeholder),
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        maxLines = 5,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        )
    )
}