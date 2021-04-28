package com.hwei.lib_common.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Query
import com.hwei.lib_common.database.bean.Concert

@Dao
interface ConcertDao {
    @Query("select * from concert")
    fun getAll(): LiveData<List<Concert>>
}