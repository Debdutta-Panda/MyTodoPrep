package com.debduttapanda.mytodoprep
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TaskAddCheckable(
    vm: AddOrEditTaskViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {
                vm.onAddCheckable()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if(isSystemInDarkTheme()) MaterialTheme.colors.background else MaterialTheme.colors.primary,
                contentColor = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
            )
        ) {
            Text(stringResource(id = R.string.add_checkable))
        }
    }
}