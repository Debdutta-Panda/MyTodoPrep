package com.debduttapanda.mytodoprep

import io.objectbox.annotation.*
import io.objectbox.relation.ToMany

@Entity
data class Task(
    @Id
    var id: Long = 0L,
    var title: String = "",
    @Index
    @Unique
    val uid: Long = 0L,
    val description: String = "",
    val dueDateTime: String = "",
    val done: Boolean = false,
){
    @Backlink(to = "task")
    lateinit var checkables: ToMany<Checkable>
}