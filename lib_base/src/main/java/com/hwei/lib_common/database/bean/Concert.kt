package com.hwei.lib_common.database.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Concert(
    @PrimaryKey
    var id:Int
)