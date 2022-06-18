package com.debduttapanda.mytodoprep

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(
    navController: NavHostController,
    vm: SplashViewModel = viewModel()
) {
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = vm.navigation.value){
        vm.navigation.forward(navController,owner)
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(

            )
            Spacer(
                modifier = Modifier.height(50.dp)
            )
            Text(
                stringResource(
                    id = R.string.app_name
                ),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary
            )
        }
    }
}