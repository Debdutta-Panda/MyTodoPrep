package com.debduttapanda.mytodoprep

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class AddTaskViewModel: ViewModel() {
    private val _description = mutableStateOf("")
    val description:State<String> = _description
    private val _title = mutableStateOf("")
    val title: State<String> = _title
    val navigation = mutableStateOf<NavigationCallback?>(null)
    fun onBackPressed() {
        navigation.navigate { navHostController, lifecycleOwner ->
            navHostController.navigateUp()
        }
    }

    fun onTitleChange(it: String) {
        _title.value = it
    }

    fun onDescriptionChange(it: String) {
        _description.value = it
    }
}