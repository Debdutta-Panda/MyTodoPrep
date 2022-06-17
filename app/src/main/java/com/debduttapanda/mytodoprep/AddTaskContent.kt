package com.debduttapanda.mytodoprep

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.debduttapanda.mytodoprep.ui.theme.checkable_header
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
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            IconButton(onClick = {
                                addTaskViewModel.onDateClick()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_calendar),
                                    contentDescription = "Date",
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                addTaskViewModel.date.value.default(stringResource(id = R.string.set_date)),
                                modifier = Modifier
                                    .clickable {
                                        addTaskViewModel.onDateClick()
                                    }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                addTaskViewModel.time.value.default(stringResource(id = R.string.set_time)),
                                modifier = Modifier
                                    .clickable {
                                        addTaskViewModel.onTimeClick()
                                    }
                            )
                            IconButton(onClick = {
                                addTaskViewModel.onTimeClick()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_clock_svgrepo_com),
                                    contentDescription = "Date",
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
            item{
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
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
            if(addTaskViewModel.checkables.size==0){
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Button(
                            onClick = {
                                addTaskViewModel.onAddCheckable()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if(isSystemInDarkTheme()) MaterialTheme.colors.background else MaterialTheme.colors.primary,
                                contentColor = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
                            )
                        ) {
                            Text(stringResource(id = R.string.add_checkable))
                        }
                    }
                }
            }
            else{
                items(
                    items = addTaskViewModel.checkables,
                    key = {
                        it.id
                    }
                ){
                    CheckableItem(addTaskViewModel,it)
                }
                item{
                    Spacer(
                        modifier = Modifier.height(60.dp)
                    )
                }
            }
        }

        /*Button(
            onClick = {
                addTaskViewModel.onAddClick()
            },
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(48.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if(isSystemInDarkTheme()) MaterialTheme.colors.background else MaterialTheme.colors.primary,
                contentColor = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
            )
        ) {
            Text(stringResource(id = R.string.add))
        }*/
    }
}

private fun String.default(value: String): String {
    if(this.isEmpty()){
        return value
    }
    return this
}
