package com.debduttapanda.mytodoprep

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class AddTaskViewModel: ViewModel() {
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

    fun onDateClick() {

    }

    fun onTimeClick() {

    }

    fun onAddCheckable(item: Checkable? = null) {
        val id = newId
        _currentFocusRequestId.value = id
        if(item==null){
            _checkables.add(Checkable(
                id,
                ""
            ))
            return
        }
        val index = _checkables.indexOfFirst {
            item.id == it.id
        }
        if(index > -1){
            _checkables.add(index+1,Checkable(
                id,
                ""
            ))
        }
        else{
            _checkables.add(Checkable(
                id,
                ""
            ))
        }
    }

    fun onAddClick() {

    }

    fun onCheckItem(item: Checkable, checked: Boolean) {
        val index = _checkables.indexOfFirst {
            item.id == it.id
        }
        _checkables[index] = item.copy(checked = checked)
    }

    fun onDeleteCheckable(item: Checkable) {
        _checkables.remove(item)
    }

    fun onValueChange(item: Checkable, value: String) {
        val index = _checkables.indexOfFirst {
            item.id == it.id
        }
        _checkables[index] = item.copy(value = value)
    }

    fun onFocusGot(item: Checkable) {
        _currentFocusRequestId.value = item.id
    }

    fun onBackOnValue(
        item: Checkable,
        currentPos: Int,
        previousPos: Int
    ) {
        if(currentPos==previousPos&&currentPos==0){
            val index = _checkables.indexOfFirst {
                item.id == it.id
            }
            if(item.value.isEmpty()){
                onDeleteCheckable(item)
            }
            if(index>0){
                _currentFocusRequestId.value = _checkables[index-1].id
            }
        }
    }
}