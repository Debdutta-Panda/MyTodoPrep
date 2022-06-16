package com.debduttapanda.mytodoprep

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController

@Composable
fun TasksContent(homeViewModel: HomeViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(homeViewModel.tasks.size==0){
            Text(
                stringResource(id = R.string.no_tasks_yet),
                color = Color.Gray
            )
        }
    }
}