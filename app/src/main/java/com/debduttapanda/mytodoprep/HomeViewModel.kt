package com.debduttapanda.mytodoprep

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.objectbox.Box
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

private val Task.sortValue: Long
    get() {
        try {
            var millis = this.updated
            if(dueDateTime.isNotEmpty()){
                val dt = this.dueDateTime
                var d = dt.date
                var t = dt.time

                if(d.isEmpty()){
                    d = today
                }
                if(t.isEmpty()){
                    t = "00:00"
                }
                val parts = d.split("-")
                val yy = parts[2].toInt()
                val mm = parts[1].toInt()
                val dd = parts[0].toInt()
                val parts1 = t.split(":")
                val h = parts1[0].toInt()
                val m = parts1[1].toInt()
                millis = DateTime(yy,mm,dd,h,m, DateTimeZone.getDefault()).millis
            }
            return millis
        } catch (e: Exception) {
            return 0L
        }
    }

class HomeViewModel: ViewModel() {
    private var job: Job? = null

    private val _askTaskDelete = mutableStateOf(false)
    val askTaskDelete: State<Boolean> = _askTaskDelete

    private val _askDeleteAll = mutableStateOf(false)
    val askDeleteAll: State<Boolean> = _askDeleteAll

    private var deletingTask: Task? = null
    val greet = mutableStateOf(false)
    val allTasks = mutableStateListOf<Task>()

    val tasksToday = mutableStateListOf<Task>()
    val tasksUpcoming = mutableStateListOf<Task>()
    val tasksRegular = mutableStateListOf<Task>()
    val tasksChecklist = mutableStateListOf<Task>()
    val tasksNote = mutableStateListOf<Task>()
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
        val all = (taskBox?.all?: emptyList()).sortedBy {
            it.sortValue
        }
        ////////////////////////
        val todays = all.filter{
            it.dueDateTime.date == date
        }
        tasksToday.clear()
        tasksToday.addAll(todays)
        ///////////////////////////
        val upcoming = all.filter {
            it.dueDateTime.date.isNotEmpty() && ((daysBetween(it.dueDateTime.date, today)
                ?: -1) > 0)
        }
        tasksUpcoming.clear()
        tasksUpcoming.addAll(upcoming)
        ///////////////////////////
        val regular = all.filter {
            val dateTime = it.dueDateTime
            val d = dateTime.date
            val t = dateTime.time
            d.isEmpty()&&t.isNotEmpty()
        }
        tasksRegular.clear()
        tasksRegular.addAll(regular)
        ///////////////////////////
        val checklists = all.filter {
            it.checkables.size>0
        }
        tasksChecklist.clear()
        tasksChecklist.addAll(checklists)
        ///////////////////////////
        val notes = all.filter {
            it.dueDateTime.isEmpty()&&it.description.isNotEmpty()&&it.checkables.size==0
        }
        tasksNote.clear()
        tasksNote.addAll(notes)
        ///////////////////////
        allTasks.clear()
        allTasks.addAll(all)
    }

    fun onAddTaskClick() {
        navigation.scope { navHostController, lifecycleOwner,toaster ->
            navHostController.navigate(Routes.addOrEdit)
        }
    }

    fun deleteAllTasks() {
        _askDeleteAll.value = true
    }

    fun onCheckableCheck(task: Task, checkable: Checkable?, checked: Boolean) {
        listOf(
            tasksToday,
            tasksUpcoming,
            tasksRegular,
            tasksChecklist,
            tasksNote,
            allTasks
        ).forEach {
            it.replace(task){ it ->
                it.checkables.replace(checkable){
                    it.checked = checked
                    it
                }
                it
            }
        }
        observeAllowed.silently {
            checkableBox?.put(checkable?.apply {
                this.checked = checked
            }?:return@silently)
        }
    }

    fun onCheckableValueChange(task: Task, checkable: Checkable, value: String) {
        listOf(
            tasksToday,
            tasksUpcoming,
            tasksRegular,
            tasksChecklist,
            tasksNote,
            allTasks
        ).forEach { it ->
            it.replace(task){ it ->
                it.checkables.replace(checkable){
                    it.value = value
                    it
                }
                it
            }
        }
        observeAllowed.silently {
            checkableBox?.put(checkable.apply {
                this.value = value
            })
        }
    }

    fun markTaskDone(task: Task) {
        val newValue = !task.done
        listOf(
            tasksToday,
            tasksUpcoming,
            tasksRegular,
            tasksChecklist,
            tasksNote,
            allTasks
        ).forEach { it ->
            it.replace(task){
                it.done = newValue
                it
            }
        }
        observeAllowed.silently {
            taskBox?.put(task.apply {
                done = newValue
            })
        }
        navigation.scope { navHostController, lifecycleOwner, toaster ->
            if(newValue){
                haveAGreet()
            }
        }
    }

    private fun haveAGreet() {
        job?.cancel()
        greet.value = false
        job = viewModelScope.launch {
            greet.value = true
            delay(3000)
            greet.value = false
        }
    }

    fun editTask(task: Task) {
        navigation.scope { navHostController, lifecycleOwner, toaster ->
            navHostController.navigate(Routes.addOrEdit+"?edit=true&taskId=${task.uid}")
        }
    }

    fun deleteTask(task: Task) {
        deletingTask = task
        _askTaskDelete.value = true
    }

    fun onAskTaskDeleteCancel() {
        deletingTask = null
        _askTaskDelete.value = false
    }

    fun confirmDeleteTask() {
        _askTaskDelete.value = false
        taskBox?.remove(deletingTask?:return)
    }

    fun confirmDeleteAll() {
        _askDeleteAll.value = false
        taskBox?.removeAll()
    }

    fun onAskDeleteAllCancel() {
        _askDeleteAll.value = false
    }
}