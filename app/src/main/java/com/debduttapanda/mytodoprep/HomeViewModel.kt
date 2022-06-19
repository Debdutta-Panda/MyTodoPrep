package com.debduttapanda.mytodoprep

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.objectbox.Box

class HomeViewModel: ViewModel() {
    private val allTasks = mutableStateListOf<Task>()
    val tasksToday = mutableStateListOf<Task>()
    val tasksUpcoming = mutableStateListOf<Task>()
    val pages = listOf(
        "Today",
        "Upcoming",
        "Regular",
        "CheckLists",
        "Notes",
        "All"
    )
    val tasks = mutableListOf<Task>()
    val observeAllowed = mutableStateOf(true)
    val navigation = mutableStateOf<UIScope?>(null)
    private var taskBox: Box<Task>? = null
    private var checkableBox: Box<Checkable>? = null
    init {
        taskBox = ObjectBox.store.boxFor(Task::class.java)
        checkableBox = ObjectBox.store.boxFor(Checkable::class.java)
        fetchAllTasks()
        ObjectBox.store.subscribe(Task::class.java)
            .observer {
                if(observeAllowed.value){
                    fetchAllTasks()
                }
            }
    }

    private fun fetchAllTasks() {
        val date = today
        val all = taskBox?.all
        val todays = all?.filter{
            it.dueDateTime.date == date
        }?: emptyList()
        val upcoming = all?.filter {
            daysBetween(it.dueDateTime.date, today)>0
        }?: emptyList()
        tasksToday.clear()
        tasksToday.addAll(todays)
        tasksUpcoming.clear()
        tasksUpcoming.addAll(upcoming)
    }

    fun onAddTaskClick() {
        navigation.scope { navHostController, lifecycleOwner,toaster ->
            navHostController.navigate("add")
        }
    }

    fun deleteAllTasks() {
        taskBox?.removeAll()
    }

    fun onCheckableCheck(task: Task, checkable: Checkable?, checked: Boolean) {
        val index = tasksToday.indexOf(task)
        if(index < -1){
            return
        }
        tasksToday.replace(task){ it ->
            it.checkables.replace(checkable){
                it.checked = checked
                it
            }
            it
        }
        observeAllowed.silently {
            checkableBox?.put(checkable?.apply {
                this.checked = checked
            }?:return@silently)
        }
    }
}