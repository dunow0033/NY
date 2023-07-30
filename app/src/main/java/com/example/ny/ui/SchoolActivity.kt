package com.example.ny.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.ny.R
import com.example.ny.databinding.ActivityMainBinding
import com.example.ny.databinding.ActivityNewSchoolBinding
import com.example.ny.databinding.SchoolsRecyclerviewItemBinding
import com.example.ny.model.SATScores
import com.example.ny.model.School
import com.example.ny.viewmodel.SATScoresViewModel

class SchoolActivity : AppCompatActivity() {

    private var school: School? = null
    private var satScoresViewModel: SATScoresViewModel? = null

    private lateinit var binding: ActivityNewSchoolBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewSchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.schoolToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //School object to setup the page
        school = intent.getParcelableExtra("School")
        binding.directionsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse(
                "geo:" + school?.latitude.toString() + "," + school?.longitude
                    .toString() + "?q=" + school?.school_name
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
//        binding.websiteButton.setOnClickListener { v ->
//            val websiteIntentUri = Uri.parse(school?.website)
//            val websiteIntent = Intent(Intent.ACTION_VIEW, websiteIntentUri)
//            v.context.startActivity(Intent.createChooser(websiteIntent, "Browse with"))
//        }
//        binding.callButton.setOnClickListener {
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:" + school?.phone_number)
//            startActivity(intent)
//        }
        binding.emailButton.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:" + school?.school_email)
            v.context.startActivity(Intent.createChooser(intent, "Email School "))
        }

        // Setup UI with School object from intent
        binding.schoolNameText.setText(school?.school_name)
        binding.schoolNameText.isSingleLine = false
        binding.schoolNameText.setHorizontallyScrolling(false)
        binding.descriptionText.setText(school?.overview_paragraph)
        binding.descriptionText.isSingleLine = false
        binding.descriptionText.setHorizontallyScrolling(false)

        // Loads SAT Scores for the School
        satScoresViewModel = ViewModelProvider(this).get(SATScoresViewModel::class.java)
        val score: LiveData<SATScores> = satScoresViewModel!!.getScoresForSchool(school?.dbn)
        score.observe(
            this
        ) { satScores -> satScoresUpdated(satScores) }
    }

    /**
     * Updates the SAT Scores once available from DB
     * @param score
     */
    private fun satScoresUpdated(score: SATScores?) {
        if (score != null) {
            val satScoresTextView = binding.satScoresTextView
            satScoresTextView.text = score.toString()
        }
    }
}