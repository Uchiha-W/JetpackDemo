package com.hwei.lib_base.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hwei.lib_base.database.bean.CookieEntity

@Dao
interface CookieDao {
    @Query("select * from cookieentity where host = :host")
    fun getAll(host: String): List<CookieEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cookieEntity: CookieEntity)

}