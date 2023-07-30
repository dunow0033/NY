package com.example.ny.database

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ny.model.SATScores

@Dao
interface SATScoresDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(scores: List<SATScores?>?)

    @Query("DELETE FROM sat_table")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM sat_table where dbn = :schoolDBN")
    fun getScore(schoolDBN: String?): LiveData<SATScores>

    @Query("SELECT * FROM sat_table ORDER BY school_name ASC")
    @Transaction
    @VisibleForTesting
    fun getAllScores(): LiveData<List<SATScores>>
}
