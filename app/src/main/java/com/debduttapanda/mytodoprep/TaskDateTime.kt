package com.debduttapanda.mytodoprep
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.mytodoprep.datetime.composematerialdialogs.MaterialDialog
import com.debduttapanda.mytodoprep.datetime.composematerialdialogs.rememberMaterialDialogState
import com.debduttapanda.mytodoprep.datetime.date.datepicker
import com.debduttapanda.mytodoprep.datetime.time.timepicker

@Composable
fun TaskDateTime(
    vm: AddOrEditTaskViewModel = viewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = {
                    vm.onDateClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Date",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    vm.date.value.default(stringResource(id = R.string.set_date)),
                    modifier = Modifier
                        .clickable {
                            vm.onDateClick()
                        }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    vm.time.value.default(stringResource(id = R.string.set_time)),
                    modifier = Modifier
                        .clickable {
                            vm.onTimeClick()
                        }
                )
                IconButton(onClick = {
                    vm.onTimeClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clock_svgrepo_com),
                        contentDescription = "Date",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    LaunchedEffect(key1 = vm.dateDialogOpen.value){
        if(vm.dateDialogOpen.value&&!dateDialogState.showing){
            dateDialogState.show()
        }
        else{
            if(dateDialogState.showing){
                dateDialogState.hide()
            }
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Ok"){
                vm.onDateDialogOk()
            }
            negativeButton("Cancel"){
                vm.onDateDialogCancel()
            }
            negativeButton("Clear"){
                vm.onDateDialogClear()
            }
        },
        onCloseRequest = {
            vm.onDateDialogCancel()
        }
    ) {
        datepicker { date ->
            vm.onDateDialogDate(date)
        }
    }

    val timeDialogState = rememberMaterialDialogState()
    LaunchedEffect(key1 = vm.timeDialogOpen.value){
        if(vm.timeDialogOpen.value&&!timeDialogState.showing){
            timeDialogState.show()
        }
        else{
            if(timeDialogState.showing){
                timeDialogState.hide()
            }
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("Ok"){
                vm.onTimeDialogOk()
            }
            negativeButton("Cancel"){
                vm.onTimeDialogCancel()
            }
            negativeButton("Clear"){
                vm.onTimeDialogClear()
            }
        },
        onCloseRequest = {
            vm.onTimeDialogCancel()
        }
    ) {
        timepicker { time ->
            vm.onTimeDialogTime(time)
        }
    }
}