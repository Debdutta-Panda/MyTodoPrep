package com.debduttapanda.mytodoprep

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val dueDateTime: String,
    val done: Boolean = false,
)
