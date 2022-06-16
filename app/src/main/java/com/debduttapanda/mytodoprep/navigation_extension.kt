package com.debduttapanda.mytodoprep

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController

typealias NavigationCallback = suspend (NavHostController,LifecycleOwner)->Unit

fun MutableState<NavigationCallback?>.navigate(block: NavigationCallback?){
    this.value = {navHostController, lifecycleOwner ->
        block?.invoke(navHostController,lifecycleOwner)
        this.value = null
    }
}

suspend fun MutableState<NavigationCallback?>.forward(navHostController: NavHostController,lifecycleOwner: LifecycleOwner){
    this.value?.invoke(navHostController,lifecycleOwner)
}