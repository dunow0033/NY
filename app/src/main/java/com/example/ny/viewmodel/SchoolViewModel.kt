package com.example.ny.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ny.model.School
import com.example.ny.repository.SchoolRepository

class SchoolViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: SchoolRepository
    private val mAllSchools: LiveData<List<School>>

    val allSchools: LiveData<List<School>>
        get() = mAllSchools

    fun getFilteredSchools(searchString: String): LiveData<List<School>> {
        return mRepository.getFilteredSchools(searchString)
    }

    fun loadSchools() {
        mRepository.loadSchools()
    }

    init {
        mRepository = SchoolRepository.getRepository(application.applicationContext)!!
        mAllSchools = mRepository.allSchools
    }
}
