package com.debduttapanda.mytodoprep

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(
    splashViewModel: SplashViewModel,
    homeViewModel: HomeViewModel,
    addTaskViewModel: AddTaskViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = "splash"
    ){
        composable(
            "splash",
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            }
        ){
            SplashScreen(splashViewModel,navController)
        }

        composable(
            "home",
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            }
        ){
            HomeScreen(homeViewModel,navController)
        }
        composable(
            "add",
            enterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
            }
        ){
            AddTaskScreen(addTaskViewModel,navController)
        }
    }
}





