package com.debduttapanda.mytodoprep

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class HomeViewModel: ViewModel() {
    val tasks = mutableListOf<Task>()
    val navigation = mutableStateOf<NavigationCallback?>(null)
    fun onAddTaskClick() {
        navigation.navigate { navHostController, lifecycleOwner ->
            navHostController.navigate("add")
        }
    }
}