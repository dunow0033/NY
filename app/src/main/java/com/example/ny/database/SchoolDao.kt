package com.example.ny.database

import androidx.lifecycle.LiveData
import com.example.ny.model.School
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(school: List<School?>?)

    @Query("DELETE FROM school_table")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM school_table ORDER BY school_name ASC")
    //val schools: LiveData<List<School>>
    fun getSchools(): LiveData<List<School>>

    @Transaction
    @Query("SELECT * FROM school_table where school_name like :searchString ORDER BY school_name ASC")
    fun getSchoolsFiltered(searchString: String): LiveData<List<School>>
}
