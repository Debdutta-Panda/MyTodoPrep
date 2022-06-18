package com.debduttapanda.mytodoprep

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.mytodoprep.ui.theme.checkable_header

@Composable
fun AddTaskContent(
    vm: AddOrEditTaskViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(24.dp)
        ){
            item(){
                TaskTitle()
            }
            item{
                24.spaceX()
            }
            item(){
                TaskDescription()
            }
            item{
                24.spaceX()
            }
            item{
                TaskDateTime()
            }
            item{
                24.spaceX()
            }
            item{
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        stringResource(id = R.string.checklist),
                        style = checkable_header,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
            if(vm.checkables.size==0){
                item{
                    TaskAddCheckable()
                }
            }
            else{
                items(
                    items = vm.checkables,
                    key = {
                        it.uid
                    }
                ){
                    CheckableItem(it)
                }
                item{
                    Spacer(
                        modifier = Modifier.height(60.dp)
                    )
                }
            }
        }
    }
}


