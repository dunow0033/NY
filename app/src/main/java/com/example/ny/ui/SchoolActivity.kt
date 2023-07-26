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
import com.example.ny.model.SATScores
import com.example.ny.model.School
import com.example.ny.viewmodel.SATScoresViewModel

class SchoolActivity : AppCompatActivity() {

    private var school: School? = null
    private var satScoresViewModel: SATScoresViewModel? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_school)
        val toolbar = findViewById<Toolbar>(R.id.school_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //School object to setup the page
        school = intent.getParcelableExtra("School")
        val directionsButton = findViewById<ImageButton>(R.id.directionsButton)
        directionsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse(
                "geo:" + school?.latitude.toString() + "," + school?.longitude
                    .toString() + "?q=" + school?.school_name
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        val websiteBUtton = findViewById<ImageButton>(R.id.websiteButton)
        websiteBUtton.setOnClickListener { v ->
            val websiteIntentUri = Uri.parse(school?.website)
            val websiteIntent = Intent(Intent.ACTION_VIEW, websiteIntentUri)
            v.context.startActivity(Intent.createChooser(websiteIntent, "Browse with"))
        }
        val callButton = findViewById<ImageButton>(R.id.callButton)
        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + school?.phone_number)
            startActivity(intent)
        }
        val emailButton = findViewById<ImageButton>(R.id.emailButton)
        emailButton.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:" + school?.school_email)
            v.context.startActivity(Intent.createChooser(intent, "Email School "))
        }

        // Setup UI with School object from intent
        val schoolNameTextView = findViewById<TextView>(R.id.schoolNameText)
        val descTextView = findViewById<TextView>(R.id.descriptionText)
        schoolNameTextView.setText(school?.school_name)
        schoolNameTextView.isSingleLine = false
        schoolNameTextView.setHorizontallyScrolling(false)
        descTextView.setText(school?.overview_paragraph)
        descTextView.isSingleLine = false
        descTextView.setHorizontallyScrolling(false)

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
            val satScoresTextView = findViewById<TextView>(R.id.satScoresTextView)
            satScoresTextView.setText(score.toString())
        }
    }
}