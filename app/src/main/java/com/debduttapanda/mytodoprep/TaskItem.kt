package com.debduttapanda.mytodoprep

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.mytodoprep.ui.theme.*

@Composable
fun TaskItem(
    task: Task,
    vm: HomeViewModel = viewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    task.title,
                    style = task_title_style,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
                TaskActions(task)
            }
            if(task.description.isNotEmpty()||task.dueDateTime.isNotEmpty()||task.checkables.size>0){
                8.spaceY()
                Divider()
            }
            if(task.description.isNotEmpty()){
                8.spaceY()
                Text(
                    task.description
                )
            }
            if(task.dueDateTime.isNotEmpty()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        task.dueDateTime.date,
                    )
                    Text(
                        task.dueDateTime.time.format12Hour,
                    )
                }
            }
            task.checkables.forEach {checkable->
                checkable?.let {
                    CheckableDisplayItem(task,it)
                }
            }
        }
    }
}

@Composable
fun TaskActions(
    task: Task,
    vm: HomeViewModel = viewModel()
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        IconButton(
            onClick = {
                vm.markTaskDone(task)
            },
            modifier = Modifier.size(24.dp)
        ) {
            if(task.done){
                Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = "Done",
                )
            }
            else{
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done",
                    tint = GreenColor
                )
            }
        }
        IconButton(
            onClick = {
                vm.editTask(task)
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = LightSkyColor
            )
        }
        IconButton(
            onClick = {
                vm.deleteTask(task)
            },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = LightRedColor
            )
        }
    }
}
