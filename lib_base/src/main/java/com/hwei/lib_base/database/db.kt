package com.hwei.lib_base.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hwei.lib_base.BaseApplication
import com.hwei.lib_base.database.bean.CookieEntity
import com.hwei.lib_base.database.convert.CookieConverter
import com.hwei.lib_base.database.dao.CookieDao

@Database(entities = [CookieEntity::class], version = 1)
@TypeConverters(CookieConverter::class)
abstract class BaseDataBase : RoomDatabase() {
    abstract fun cookieDao(): CookieDao
}

val AppDataBase =
    Room.databaseBuilder(BaseApplication.context, BaseDataBase::class.java, "db").build()
