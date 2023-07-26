package com.example.ny.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ny.R
import com.example.ny.model.School

class SchoolViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val schoolItemView: TextView
    private var school: School? = null

    fun bind(school: School) {
        this.school = school
        schoolItemView.setText(school.school_name)
    }

    companion object {
        fun create(parent: ViewGroup): SchoolViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.schools_simple_recyclerview_item, parent, false)
            return SchoolViewHolder(view)
        }
    }

    init {
        itemView.setOnClickListener { v ->
            val intent = Intent(v.context, SchoolActivity::class.java)
            intent.putExtra("School", school)
            v.context.startActivity(intent)
        }
        schoolItemView = itemView.findViewById(R.id.textView)
    }
}
