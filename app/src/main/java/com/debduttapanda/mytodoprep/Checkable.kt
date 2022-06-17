package com.debduttapanda.mytodoprep

data class Checkable(
    var id: Long,
    var value: String,
    var checked: Boolean = false
)
