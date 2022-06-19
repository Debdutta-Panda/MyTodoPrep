package com.debduttapanda.mytodoprep

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TasksContent(
    navController: NavHostController,
    vm: HomeViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState()
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .fillMaxWidth()
                            .pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                }
            ){
                TaskFilters.values().forEachIndexed{ index, filter ->
                    Tab(
                        text = { Text(filter.name) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                count = TaskFilters.values().size,
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {page->
                when(TaskFilters.values()[page]){
                    TaskFilters.Today -> TaskFilter(vm.tasksToday)
                    TaskFilters.Upcoming -> TaskFilter(vm.tasksUpcoming)
                    TaskFilters.Regular -> TaskFilter(vm.tasksRegular)
                    TaskFilters.CheckLists -> TaskFilter(vm.tasksChecklist)
                    TaskFilters.Notes -> TaskFilter(vm.tasksNote)
                    TaskFilters.All -> TaskFilter(vm.allTasks)
                }
            }
        }
    }
}

@Composable
fun TaskFilter(tasks: SnapshotStateList<Task>) {
    if(tasks.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                stringResource(id = R.string.no_tasks_yet),
                color = Color.Gray
            )
        }
    }
    else{
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 60.dp,
                start = 16.dp,
                end = 16.dp
            )
        ){
            items(tasks){
                TaskItem(it)
            }
        }
    }
}
