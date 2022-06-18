package com.debduttapanda.mytodoprep

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.debduttapanda.mytodoprep.ui.theme.task_title_style

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                Log.d("flfjflkfkldsf","value=${task.dueDateTime}end")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(Color.Red),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        task.dueDateTime.split(" ").first(),
                    )
                    Text(
                        task.dueDateTime.split(" ").last(),
                    )
                }
            }
        }
    }
}