package com.example.ny.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ny.model.SATScores
import com.example.ny.repository.SchoolRepository

class SATScoresViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: SchoolRepository

    fun getScoresForSchool(schoolDBN: String?): LiveData<SATScores> {
        return mRepository.getSATScoresForSchool(schoolDBN)
    }

    init {
        mRepository = SchoolRepository.getRepository(application.applicationContext)!!
    }
}
