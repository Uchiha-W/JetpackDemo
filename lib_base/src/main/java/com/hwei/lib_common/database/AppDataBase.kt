package com.hwei.lib_common.database

import androidx.room.Room
import com.hwei.lib_common.BaseApplication

object AppDataBase {
    val db = Room.databaseBuilder(BaseApplication.context, BaseDataBase::class.java, "db").build()
}