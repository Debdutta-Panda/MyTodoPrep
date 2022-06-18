package com.debduttapanda.mytodoprep

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.LocalTime

data class Toaster(
    private val context: Context
){
    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(context, message,duration).show()
    }

    fun stringResource(@StringRes id: Int): String{
        return context.getString(id)
    }
}

typealias UIScope = suspend (NavHostController, LifecycleOwner,Toaster?)->Unit

fun MutableState<UIScope?>.scope(block: UIScope?){
    this.value = {navHostController, lifecycleOwner,toaster ->
        block?.invoke(navHostController,lifecycleOwner,toaster)
        this.value = null
    }
}

suspend fun MutableState<UIScope?>.forward(
    navHostController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    toaster: Toaster? = null
){
    this.value?.invoke(navHostController,lifecycleOwner,toaster)
}

fun String.default(value: String): String {
    if(this.isEmpty()){
        return value
    }
    return this
}

@Composable
fun Number.spaceX()
{
    Spacer(modifier = Modifier.width(this.toFloat().dp))
}
@Composable
fun Number.spaceY()
{
    Spacer(modifier = Modifier.height(this.toFloat().dp))
}

val LocalDate.formatted: String
get() {
    val y = this.year
    val m = this.monthValue
    val d = this.dayOfMonth
    return String.format("%d-%02d-%02d",y,m,d)
}

val LocalTime.formatted: String
get() {
    val h = this.hour
    val m = this.minute
    return String.format("%02d:%02d",h,m)
}