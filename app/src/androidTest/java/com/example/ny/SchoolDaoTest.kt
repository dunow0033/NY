package com.example.ny

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.ny.database.SchoolDao
import com.example.ny.database.SchoolRoomDatabase
import com.example.ny.model.School
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SchoolDaoTest {
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var schoolDao: SchoolDao? = null
    private var db: SchoolRoomDatabase? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, SchoolRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        schoolDao = db?.schoolDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        db?.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertDeleteGetAll() {
        val school1 = School("id_school_1")
        val school2 = School("id_school_2")
        val schools: MutableList<School> = ArrayList<School>()
        schools.add(school1)
        schools.add(school2)
        schoolDao?.insertAll(schools)
        var allSchools: List<School>? = schoolDao?.let { LiveDataTestUtil.getValue(it.getSchools()) }
        assertEquals(allSchools!![0].school_name, school1.school_name)
        assertEquals(allSchools!![1].school_name, school2.school_name)
        schoolDao?.deleteAll()
        allSchools = schoolDao?.let { LiveDataTestUtil.getValue(it.getSchools()) }
        Assert.assertTrue(allSchools!!.isEmpty())
    }
}