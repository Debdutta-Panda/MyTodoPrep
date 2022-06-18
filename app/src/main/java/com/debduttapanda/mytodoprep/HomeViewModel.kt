package com.debduttapanda.mytodoprep

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.objectbox.Box

class HomeViewModel: ViewModel() {
    val tasksToday = mutableStateListOf<Task>()
    val pages = listOf(
        "Today",
        "Upcoming",
        "CheckLists",
        "Notes",
        "All"
    )
    val tasks = mutableListOf<Task>()
    val navigation = mutableStateOf<UIScope?>(null)
    private var taskBox: Box<Task>? = null
    init {
        taskBox = ObjectBox.store.boxFor(Task::class.java)
        fetchAllTasks()
        ObjectBox.store.subscribe(Task::class.java)
            .observer {
                fetchAllTasks()
            }
    }

    private fun fetchAllTasks() {
        taskBox?.all?.let {
            tasksToday.clear()
            tasksToday.addAll(it)
        }
    }

    fun onAddTaskClick() {
        navigation.scope { navHostController, lifecycleOwner,toaster ->
            navHostController.navigate("add")
        }
    }

    fun deleteAllTasks() {
        taskBox?.removeAll()
    }
}