package com.debduttapanda.mytodoprep

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController) {
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = homeViewModel.navigation.value){
        homeViewModel.navigation.forward(navController,owner)
    }
    val config = LocalConfiguration.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeViewModel.onAddTaskClick()
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        TasksContent(homeViewModel,navController)
    }
}


