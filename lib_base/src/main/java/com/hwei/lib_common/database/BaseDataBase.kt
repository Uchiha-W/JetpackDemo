package com.hwei.lib_common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hwei.lib_common.database.bean.Concert
import com.hwei.lib_common.database.dao.ConcertDao

@Database(entities = [Concert::class], version = 1)
abstract class BaseDataBase : RoomDatabase() {
    abstract fun concertDao(): ConcertDao
}
