package com.debduttapanda.mytodoprep

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.debduttapanda.mytodoprep.ui.theme.LightRedColor
import com.debduttapanda.mytodoprep.ui.theme.dialog_message_style
import com.debduttapanda.mytodoprep.ui.theme.delete_dialog_title_style
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    vm: HomeViewModel = viewModel()
) {
    val owner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.navigation.value){
        vm.navigation.forward(navController,owner, Toaster(context))
    }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 64.dp
                            )
                        )
                        .background(
                            if (isSystemInDarkTheme()) MaterialTheme.colors.background else MaterialTheme.colors.primary
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_todo_list_svgrepo_com),
                            contentDescription = "Logo",
                            tint = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else Color.White,
                            modifier = Modifier.size(80.dp)
                        )
                        12.spaceY()
                        Text(
                            stringResource(
                                id = R.string.app_name
                            ),
                            style = MaterialTheme.typography.h4,
                            color = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            vm.deleteAllTasks()
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = LightRedColor
                    )
                    24.spaceX()
                    Text("Delete all tasks")
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, "")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.onAddTaskClick()
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        TasksContent(navController)
        if(vm.greet.value){
            LottieView(
                json = R.raw.thumbs_up,
                iterations = 1,
                modifier = Modifier.fillMaxSize()
            )
        }
        if(vm.askTaskDelete.value){
            AskTaskDelete()
        }
        if(vm.askDeleteAll.value){
            AskDeleteAll()
        }
    }
}
