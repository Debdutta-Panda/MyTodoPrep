package com.debduttapanda.mytodoprep

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.mytodoprep.ui.theme.checkable_checked_style
import com.debduttapanda.mytodoprep.ui.theme.checkable_unchecked_style
import com.debduttapanda.mytodoprep.ui.theme.task_title_style

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
            if(task.title.isNotEmpty()){
                Text(
                    task.title,
                    style = task_title_style
                )
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Checkbox(
                        checked = checkable.checked,
                        onCheckedChange = {checked->
                            vm.onCheckableCheck(task,checkable,checked)
                        }
                    )
                    if(checkable.checked){
                        Text(
                            checkable.value,
                            style = checkable_checked_style
                        )
                    }
                    else{
                        Text(
                            checkable.value,
                            style = checkable_unchecked_style
                        )
                    }
                }
            }
        }
    }
}