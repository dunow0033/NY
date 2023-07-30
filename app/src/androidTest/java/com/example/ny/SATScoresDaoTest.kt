package com.example.ny

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ny.database.SATScoresDao
import com.example.ny.database.SchoolRoomDatabase
import com.example.ny.model.SATScores
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SATScoresDaoTest {
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var scoresDao: SATScoresDao? = null
    private var db: SchoolRoomDatabase? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, SchoolRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        scoresDao = db!!.satScoresDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        db.close()
    }

    /**
     * Testing insertion deletion of SATScores
     * @throws Exception
     */
    @get:Throws(Exception::class)
    @get:Test
    val insertAllDeleteAll: Unit
        get() {
            val score1 = SATScores("id_school_1")
            val score2 = SATScores("id_school_2")
            val scores: MutableList<SATScores?> = ArrayList<SATScores?>()
            scores.add(score1)
            scores.add(score2)
            scoresDao.insertAll(scores)
            val testScores: SATScores? =
                scoresDao?.let { LiveDataTestUtil.getValue(it.getScore(score1.dbn)) }
            assertEquals(testScores!!.dbn, score1.dbn)
            val allScores: List<SATScores>? = scoresDao?.let { LiveDataTestUtil.getValue(it.getAllScores()) }
            assertEquals(allScores?.get(0)?.dbn ?: null, score1.dbn)
            assertEquals(allScores?.get(0)?.dbn ?: null, score2.dbn)
            scoresDao?.deleteAll()
            val allSchools: List<SATScores>? = scoresDao?.let { LiveDataTestUtil.getValue(it.getAllScores()) }
            Assert.assertTrue(allSchools!!.isEmpty())
        }
}