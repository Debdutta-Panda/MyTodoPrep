package com.debduttapanda.mytodoprep

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class SplashViewModel: ViewModel() {
    val navigation = mutableStateOf<NavigationCallback?>(null)
    init {
        navigation.navigate{navHostController, lifecycleOwner ->
            delay(4000L)
            navHostController.popBackStack()
            navHostController.navigate("home")
        }
    }
}