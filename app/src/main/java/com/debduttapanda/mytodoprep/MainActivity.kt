package com.debduttapanda.mytodoprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.debduttapanda.mytodoprep.ui.theme.MyTodoPrepTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashViewModel: SplashViewModel by viewModels()
        val homeViewModel: HomeViewModel by viewModels()
        val addTaskViewModel: AddTaskViewModel by viewModels()
        setContent {
            MyTodoPrepTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent(
                        splashViewModel,
                        homeViewModel,
                        addTaskViewModel
                    )
                }
            }
        }
    }
}