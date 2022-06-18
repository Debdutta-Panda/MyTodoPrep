package com.debduttapanda.mytodoprep

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
                vm.pages.forEachIndexed{ index, title ->
                    Tab(
                        text = { Text(title) },
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
                count = vm.pages.size,
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {page->
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    when(page){
                        0->items(vm.tasksToday){
                            TaskItem(it)
                        }
                    }
                }
            }
        }
    }
}