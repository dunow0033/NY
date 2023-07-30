package com.example.ny.repository

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import com.example.ny.database.SATScoresDao
import com.example.ny.database.SchoolDao
import com.example.ny.database.SchoolRoomDatabase
import com.example.ny.model.SATScores
import com.example.ny.model.School
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

class SchoolRepository internal constructor(context: Context?) {

    private val mSchoolDao: SchoolDao
    private val mScoresDao: SATScoresDao
    private val mAllSchools: LiveData<List<School>>

    /**
     * Will fetch Schools list as LiveData so that it can be executed in the background
     * @return
     */
    val allSchools: LiveData<List<School>>
        get() = mAllSchools

    /**
     * Search in DB
     * @param searchString
     * @return
     */
    fun getFilteredSchools(searchString: String): LiveData<List<School>> {
        return mSchoolDao.getSchoolsFiltered(searchString)
    }

    /**
     * Get SATScores for School DBN
     * @param schoolDBN
     * @return
     */
    fun getSATScoresForSchool(schoolDBN: String?): LiveData<SATScores> {
        return mScoresDao.getScore(schoolDBN)
    }

    /**
     * Insert Schools into DB in background
     * @param schools
     */
    fun insertSchools(schools: List<School?>?) {
        SchoolRoomDatabase.databaseWriteExecutor.execute { mSchoolDao.insertAll(schools) }
    }

    /**
     * Insert Scores into DB in background
     * @param scores
     */
    fun insertScores(scores: List<SATScores?>?) {
        SchoolRoomDatabase.databaseWriteExecutor.execute { mScoresDao.insertAll(scores) }
    }

    /**
     * From here lies all code related to REST API calls using OKHTTP.
     * We can put them in another class to handle them.
     */
    fun loadSchools() {
        fetchSchoolsData()
        fetchSATScores()
    }

    /**
     * Fetches School Data from NYC Schools API
     */
    fun fetchSchoolsData() {
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .build()
        val request: Request = Request.Builder()
            .url(NYC_SCHOOLS_URL)
            .method("GET", null)
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val jsonData: String = response.body!!.string()
                    // Load data as School Object using Gson
                    val listType: Type = object : TypeToken<List<School>>() {}.type
                    val schools: List<School?> = Gson().fromJson(jsonData, listType)
                    insertSchools(schools)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Fetches SATScores Data from NYC Schools API
     */
    fun fetchSATScores() {
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .build()
        val request: Request = Request.Builder()
            .url(NYC_SCHOOLS_SAT_SCORES_URL)
            .method("GET", null)
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val jsonData: String = response.body!!.string()
                    // Load data as SATScores Object using Gson
                    val listType: Type = object : TypeToken<List<SATScores>>() {}.type
                    val satScores: List<SATScores?> = Gson().fromJson(jsonData, listType)
                    insertScores(satScores)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val NYC_SCHOOLS_URL = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json"
        private const val NYC_SCHOOLS_SAT_SCORES_URL = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json"

        @Volatile
        private var INSTANCE: SchoolRepository? = null

        /**
         * Singleton Instance
         * @param context
         * @return
         */
        fun getRepository(context: Context?): SchoolRepository? {
            if (INSTANCE == null) {
                synchronized(SchoolRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = SchoolRepository(context)
                    }
                }
            }
            return INSTANCE
        }
    }

    init {
        val db: SchoolRoomDatabase? = SchoolRoomDatabase.getDatabase(context!!)
        mSchoolDao = db!!.schoolDao()
        mScoresDao = db.satScoresDao()
        mAllSchools = mSchoolDao.getSchools()
    }
}
