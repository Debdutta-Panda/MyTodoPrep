package com.debduttapanda.mytodoprep

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddOrEditTaskScreen(
    navController: NavHostController,
    edit: Boolean?,
    taskId: Long?,
    vm: AddOrEditTaskViewModel = viewModel()
) {
    LaunchedEffect(key1 = Unit){
        vm.setArguments(edit,taskId)
    }
    val owner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.navigation.value){
        vm.navigation.forward(navController,owner,Toaster(context))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(vm.editing) stringResource(id = R.string.edit_task) else  stringResource(id = R.string.add_task),
                        color = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            vm.onBackPressed()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.onTaskAdd()
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save")
                    }
                }
            )
        }
    ) {
        AddTaskContent()
    }
}
