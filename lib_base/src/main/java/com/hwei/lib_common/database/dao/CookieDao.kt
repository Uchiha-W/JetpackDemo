package com.hwei.lib_common.database.dao

import androidx.room.*
import com.hwei.lib_common.database.bean.CookieEntity
import okhttp3.Cookie

@Dao
interface CookieDao {
    @Query("select * from cookieentity where host = :host")
    fun getAll(host: String): List<CookieEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cookieEntity: CookieEntity)

}