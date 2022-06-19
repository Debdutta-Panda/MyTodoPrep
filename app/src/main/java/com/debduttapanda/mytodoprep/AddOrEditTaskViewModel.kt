package com.debduttapanda.mytodoprep

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import io.objectbox.Box
import java.time.LocalDate
import java.time.LocalTime



class AddOrEditTaskViewModel: ViewModel() {
    private var task: Task? = null
    private var _editing = false
    val editing: Boolean
    get(){
        return _editing
    }
    private var taskId = 0L
    private val _dateDialogOpen = mutableStateOf(false)
    val dateDialogOpen:State<Boolean> = _dateDialogOpen
    private val _timeDialogOpen = mutableStateOf(false)
    val timeDialogOpen:State<Boolean> = _timeDialogOpen
    private fun clearAll() {
        task = null
        _currentFocusRequestId.value = -1L
        _checkables.clear()
        _time.value = ""
        _date.value = ""
        _description.value = ""
        _title.value = ""
    }
    private val _currentFocusRequestId = mutableStateOf(-1L)
    val currentFocusRequestId: State<Long> = _currentFocusRequestId
    private val newId: Long
    get(){
        return System.currentTimeMillis()
    }
    private val _checkables = mutableStateListOf<Checkable>()
    val checkables: SnapshotStateList<Checkable> = _checkables

    private val _time = mutableStateOf("")
    val time:State<String> = _time

    private val _date = mutableStateOf("")
    val date:State<String> = _date

    private val _description = mutableStateOf("")
    val description:State<String> = _description

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    val navigation = mutableStateOf<UIScope?>(null)
    //////////////////////
    private var taskBox: Box<Task>? = null
    init {
        taskBox = ObjectBox.store.boxFor(Task::class.java)
    }
    ///////////////////////
    fun onBackPressed() {
        navigation.scope { navHostController, lifecycleOwner,toaster ->
            navHostController.navigateUp()
        }
    }

    fun onTitleChange(it: String) {
        _title.value = it
    }

    fun onDescriptionChange(it: String) {
        _description.value = it
    }

    fun onDateClick() {
        _dateDialogOpen.value = true
    }

    fun onTimeClick() {
        _timeDialogOpen.value = true
    }

    fun onAddCheckable(item: Checkable? = null) {
        val id = newId
        val checkable = Checkable(
            uid = id,
        )
        _currentFocusRequestId.value = id
        if(item==null){
            _checkables.add(checkable)
            return
        }
        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }
        if(index > -1){
            _checkables.add(index+1,checkable)
        }
        else{
            _checkables.add(checkable)
        }
    }

    fun onTaskAdd() {
        if(!canAdd()){
            navigation.scope { navHostController, lifecycleOwner, toaster ->
                toaster?.toast(toaster.stringResource(R.string.please_put_title_description_or_checkables))
            }
            return
        }
        taskBox?.put(
            getTaskToAdd()?:return
        )
        clearAll()
        navigation.scope { navHostController, lifecycleOwner, toaster ->
            toaster?.toast(
                if(editing) toaster.stringResource(R.string.task_updated_successfully) else toaster.stringResource(R.string.task_added_successfully)
            )
            navHostController.popBackStack()
        }
    }

    private fun getTaskToAdd(): Task? {
        if(!_editing){
            return Task(
                title = _title.value,
                description = _description.value,
                dueDateTime = (_date.value+" "+_time.value).trim(),
                uid = newId,
                done = false,
                created = nowMillis,
                updated = nowMillis
            ).apply {
                checkables.addAll(_checkables.filter {
                    it.value.isNotEmpty()
                })
            }
        }
        else{
            return task?.copy(
                title = _title.value,
                description = _description.value,
                dueDateTime = (_date.value+" "+_time.value).trim(),
                uid = newId,
                updated = nowMillis
            ).apply {
                checkables.addAll(_checkables.filter {
                    it.value.isNotEmpty()
                })
            }
        }
    }


    private fun canAdd(): Boolean {
        return _title.value.isNotEmpty()
                ||_description.value.isNotEmpty()
                ||(_checkables.count {
                    it.value.isNotEmpty()
        } > 0)
    }

    fun onCheckableCheck(item: Checkable, checked: Boolean) {
        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }
        _checkables[index] = item.copy(checked = checked)
    }

    fun onCheckableDelete(item: Checkable) {
        _checkables.remove(item)
    }

    fun onCheckableValueChange(item: Checkable, value: String) {
        val index = _checkables.indexOfFirst {
            item.uid == it.uid
        }
        _checkables[index] = item.copy(value = value)
    }

    fun onFocusGot(item: Checkable) {
        _currentFocusRequestId.value = item.uid
    }

    fun onBackOnValue(
        item: Checkable,
        currentPos: Int,
        previousPos: Int
    ) {
        if(currentPos==previousPos&&currentPos==0){
            val index = _checkables.indexOfFirst {
                item.uid == it.uid
            }
            if(item.value.isEmpty()){
                onCheckableDelete(item)
            }
            if(index>0){
                _currentFocusRequestId.value = _checkables[index-1].uid
            }
        }
    }

    fun onDateDialogDismissRequest() {
        _dateDialogOpen.value = false
    }

    fun onDateDialogCancel() {
        _dateDialogOpen.value = false
    }

    fun onDateDialogOk() {
        _dateDialogOpen.value = false
    }

    fun onDateDialogDate(date: LocalDate) {
        _date.value = date.formatted
    }

    fun onTimeDialogOk() {
        _timeDialogOpen.value = false
    }

    fun onTimeDialogCancel() {
        _timeDialogOpen.value = false
    }

    fun onTimeDialogTime(time: LocalTime) {
        _time.value = time.formatted
    }

    fun onDateDialogClear() {
        _dateDialogOpen.value = false
        _date.value = ""
    }

    fun onTimeDialogClear() {
        _timeDialogOpen.value = false
        _time.value = ""
    }
    fun setArguments(edit: Boolean?, taskId: Long?) {
        _editing = edit?:false
        this.taskId = taskId?:0L
        loadTask()
    }

    private fun loadTask() {
        if(taskId==0L){
            return
        }
        val query = taskBox?.query()?.equal(Task_.uid, taskId)?.build()
        val tasks = query?.find()?: emptyList()
        if(tasks.isNotEmpty()){
            task = tasks.first()
            _checkables.addAll(task?.checkables?: emptyList())
            _time.value = task?.dueDateTime?.time?:""
            _date.value = task?.dueDateTime?.date?:""
            _description.value = task?.description?:""
            _title.value = task?.title?:""
        }
    }
}