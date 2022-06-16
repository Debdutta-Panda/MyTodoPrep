package com.debduttapanda.mytodoprep

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CalendarViewDay
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debduttapanda.mytodoprep.ui.theme.field_placeholder_big

@Composable
fun AddTaskContent(addTaskViewModel: AddTaskViewModel) {
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
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = addTaskViewModel.title.value,
                    onValueChange = {
                        addTaskViewModel.onTitleChange(it)
                    },
                    placeholder = {
                        Text(
                            stringResource(id = R.string.new_task_title_placeholder),
                            style = field_placeholder_big
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    ),
                    textStyle = field_placeholder_big,
                )
            }
            item{
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
            item(){
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = addTaskViewModel.description.value,
                    onValueChange = {
                        addTaskViewModel.onDescriptionChange(it)
                    },
                    placeholder = {
                        Text(
                            stringResource(id = R.string.new_task_description_placeholder),
                        )
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.new_task_description_placeholder),
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    ),
                    maxLines = 5
                )
            }
            item{
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
            item{
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Filled.CalendarViewMonth,
                                contentDescription = "Date",
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
            }
        }
    }
}