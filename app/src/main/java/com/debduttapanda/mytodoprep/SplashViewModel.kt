package com.debduttapanda.mytodoprep

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class SplashViewModel: ViewModel() {
    val navigation = mutableStateOf<UIScope?>(null)
    init {
        navigation.scope{ navHostController, lifecycleOwner, toaster ->
            delay(4000L)
            navHostController.popBackStack()
            navHostController.navigate(Routes.home)
        }
    }
}