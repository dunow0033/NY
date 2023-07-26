package com.example.ny.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ny.model.School
import com.example.ny.ui.SchoolViewHolder


class SchoolListAdapter(diffCallback: SchoolDiff) : ListAdapter<School, SchoolViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val current: School = getItem(position)
        holder.bind(current)
    }

    class SchoolDiff: DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.school_name == newItem.school_name
        }
    }
}
