package com.debduttapanda.mytodoprep

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToOne

@Entity
data class Checkable(
    @Id
    var id: Long = 0L,
    @Index
    @Unique
    var value: String = "",
    var uid: Long = 0L,
    var checked: Boolean = false
){
    lateinit var task: ToOne<Task>
}
